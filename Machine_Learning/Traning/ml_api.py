import pandas as pd
import json
import joblib
import lightgbm as lgb
from fastapi import FastAPI, HTTPException, UploadFile, File
from fastapi.responses import FileResponse
from pydantic import BaseModel
from typing import List, Optional, Any
from contextlib import asynccontextmanager
import io
import os
from fastapi import FastAPI, UploadFile, File
import pandas as pd
import requests
import io


from ml_function import predict_and_explain


loaded_models = {}
MODEL_DIR = './models'
model_paths = {
    "lgb": "models/lgb_model.txt",
    "iso": "models/isolation_forest.joblib",
    "meta": "models/meta.json"
}


app = FastAPI(title="Fraud Detection API")

class Transaction(BaseModel):
    transaction_id: Optional[str] = None
    user_id: int
    timestamp: str 
    amount: float
    merchant_category: str
    merchant: str 
    location: str
    device_id: str
    ip_address: str
    class Config:
        extra = "allow"

@app.post("/predict")
async def predict_json(transactions: List[Transaction]):
    if not loaded_models:
        raise HTTPException(status_code=500, detail="Models not loaded")
    
    # Convert list of Pydantic models to DataFrame
    data = [t.dict() for t in transactions]
    df = pd.DataFrame(data)
    

    try:
        # We pass empty model_paths because we provide loaded_models
        df_out = predict_and_explain(df, model_paths)
        
        # Save to predicted_output.csv (as requested to have an endpoint for it)
        df_out.to_csv("predicted_output.csv", index=False)
        
        # Return JSON
        # Convert NaN to None for JSON compliance
        result = df_out.to_dict(orient="records")
        for record in result:
            for key, value in record.items():
                if isinstance(value, float) and (value != value): # pd.isna(value) check for float
                     record[key] = None
                # specific check for explain field if it contains nans inside
                
        return result
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Prediction error: {str(e)}")

@app.post("/predict_csv")
async def predict_csv(file: UploadFile = File(...)):
    # if not loaded_models:
    #     raise HTTPException(status_code=500, detail="Models not loaded")
    
    try:
        contents = await file.read()
        df = pd.read_csv(io.BytesIO(contents))
        
        # Run prediction
        df_out = predict_and_explain(df, model_paths)
        
        # Save to predicted_output.csv
        df_out.to_csv("predicted_output.csv", index=False)
        
        # Return as downloadable file
        return FileResponse("predicted_output.csv", media_type='text/csv', filename="predicted_output.csv")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Prediction error: {str(e)}")

@app.get("/predicted_output.csv")
async def get_predicted_output():
    if not os.path.exists("predicted_output.csv"):
        raise HTTPException(status_code=404, detail="No prediction output found yet.")
    return FileResponse("predicted_output.csv", media_type='text/csv', filename="predicted_output.csv")




OLLAMA_URL = "http://localhost:11434/api/generate"
MODEL_NAME = "phi3"


# --------------------------------------------------
# RULE CONFIG (EASY TO EXTEND)
# --------------------------------------------------

# Binary flags (0 / 1)
BINARY_RULES = {
    "is_night": "Transaction occurred during unusual night-time hours",
    "location_change": "Transaction location changed suddenly compared to prior activity",
    "is_round_amount": "Transaction amount is a perfectly rounded value"
}

# Numeric rules (threshold based)
NUMERIC_RULES = {
    "amount_z_global": {
        "threshold": 3,
        "explanation": "Transaction amount is a global statistical outlier"
    }
}


# --------------------------------------------------
# PROMPT (FACT-ONLY, NO HALLUCINATION)
# --------------------------------------------------
def build_prompt(active_conditions: list, anomaly_pct: float | None) -> str:
    facts = active_conditions.copy()

    if anomaly_pct is not None:
        facts.append(f"Anomaly percentage recorded at {anomaly_pct:.2f} percent")

    numbered_facts = "\n".join(
        [f"{i+1}. {fact}" for i, fact in enumerate(facts)]
    )

    return f"""
You are a sentence rewriter for a banking fraud system.

STRICT RULES (DO NOT BREAK):
- You may ONLY rephrase the sentences provided below
- Do NOT add new facts, causes, interpretations, or examples
- Do NOT mention fraud types, crimes, or intentions
- Do NOT use words like "fraud", "money laundering", "scam", "criminal", "illegal"
- Do NOT add conclusions or summaries
- Keep the SAME number of sentences
- Keep the SAME meaning
- Neutral, professional tone
- No emojis

INPUT SENTENCES:
{numbered_facts}

TASK:
Rewrite each sentence slightly to sound clearer and more natural.
Output ONLY the rewritten sentences in the same numbered format.
"""



# --------------------------------------------------
# CALL OLLAMA
# --------------------------------------------------
def call_ollama(prompt: str) -> str:
    payload = {
        "model": MODEL_NAME,
        "prompt": prompt,
        "stream": False
    }
    response = requests.post(OLLAMA_URL, json=payload)
    return response.json()["response"].strip()


# --------------------------------------------------
# FORMAT OUTPUT (DUKANDAR STYLE)
# --------------------------------------------------
def format_explanation(raw: str) -> str:
    lines = [
        l.strip().lstrip("0123456789. ").strip()
        for l in raw.split("\n")
        if l.strip()
    ]

    emojis = ["⚠️", "⏱️", "🔍"]

    final = []
    for i, line in enumerate(lines[:3]):
        emoji = emojis[i] if i < len(emojis) else ""
        final.append(f"{emoji} {line}")

    return "\n".join(final)


def safe_explanation(original_facts: list, rewritten: str) -> str:
    banned_words = [
        "fraud", "launder", "criminal", "illegal", "scheme",
        "scam", "cyber", "money", "intent"
    ]

    for word in banned_words:
        if word in rewritten.lower():
            return "\n".join(original_facts[:3])

    return rewritten



@app.post("/analyze-csv")
async def analyze_csv(file: UploadFile = File(...)):
    contents = await file.read()
    df = pd.read_csv(io.BytesIO(contents))

    results = []

    total_tx = len(df)
    fraud_count = 0
    total_amount = 0.0

    # 🔥 Analytics counters
    fraud_type_counter = {}
    region_counter = {}

    for _, row in df.iterrows():

        # ---------- Amount aggregation ----------
        if "amount" in row and pd.notna(row["amount"]):
            total_amount += float(row["amount"])

        # ---------- Anomaly percentage ----------
        anomaly_pct = None
        if "anomaly_percentage" in row and pd.notna(row["anomaly_percentage"]):
            anomaly_pct = float(row["anomaly_percentage"])

        # ==================================================
        # ✅ STEP 1: CHECK ML PREDICTION FIRST
        # ==================================================
        is_fraud = False
        if "predicted_fraud" in row and int(row["predicted_fraud"]) == 1:
            is_fraud = True

        # ---------- NOT FRAUD ----------
        if not is_fraud:
            results.append({
                "transaction_id": row.get("transaction_id"),
                "user_id": row.get("user_id"),
                "amount": row.get("amount"),
                "location": row.get("location"),
                "risk_assessment": "No suspicious activity detected.",
                "is_fraud": is_fraud,
                "anomaly_pct": anomaly_pct
            })
            continue

        # ==================================================
        # 🚨 FRAUD CONFIRMED BY ML
        # ==================================================
        fraud_count += 1
        active_conditions = []

        # ---------- Binary rules ----------
        for col, explanation in BINARY_RULES.items():
            if col in row and row[col] == 1:
                active_conditions.append(explanation)
                fraud_type_counter[explanation] = fraud_type_counter.get(explanation, 0) + 1

        # ---------- Numeric rules ----------
        for col, rule in NUMERIC_RULES.items():
            if col in row and pd.notna(row[col]) and row[col] > rule["threshold"]:
                active_conditions.append(rule["explanation"])
                fraud_type_counter[rule["explanation"]] = fraud_type_counter.get(rule["explanation"], 0) + 1



        # ---------- Region counter ----------
        location = row.get("location")
        if location:
            region_counter[location] = region_counter.get(location, 0) + 1

        print("Conditions: " , active_conditions)
        print("Anomaly Percentage: ", anomaly_pct)

        # ---------- LLM explanation ----------
        prompt = build_prompt(active_conditions, anomaly_pct)
        raw = call_ollama(prompt)
        clean = safe_explanation(active_conditions, raw)
        explanation = format_explanation(clean)

        print("Explanation: ", explanation)

        results.append({
            "transaction_id": row.get("transaction_id"),
            "user_id": row.get("user_id"),
            "amount": row.get("amount"),
            "location": location,
            "risk_assessment": explanation,
            "is_fraud": is_fraud,
            "anomaly_pct": anomaly_pct
        })

    # ==================================================
    # 📊 FINAL ANALYTICS
    # ==================================================
    fraud_percentage = (fraud_count / total_tx) * 100 if total_tx else 0

    summary = {
        "total_transactions": total_tx,
        "flagged_transactions": fraud_count,
        "fraud_percentage": round(fraud_percentage, 2)
    }

    # ---------- PIE CHART ----------
    fraud_type_distribution = {}
    if fraud_count > 0:
        for fraud_type, count in fraud_type_counter.items():
            fraud_type_distribution[fraud_type] = count

    # ---------- METRICS ----------
    avg_amount = (total_amount / total_tx) if total_tx else 0
    top_risk_region = max(region_counter, key=region_counter.get) if region_counter else None

    metrics = {
        "average_transaction_amount": round(avg_amount, 2),
        "top_risk_region": top_risk_region
    }

    return {
        "summary": summary,
        "fraud_type_distribution": fraud_type_distribution,
        "metrics": metrics,
        "results": results
    }




if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
