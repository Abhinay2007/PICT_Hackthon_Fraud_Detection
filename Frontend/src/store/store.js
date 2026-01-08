import { configureStore, createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const BASE_BACKEND_API = "http://localhost:8080/api/";

export const sendFile = createAsyncThunk("send-file",
  async (file, {dispatch}) => {
  if (!file) {
    console.warn("No file selected");
    return null;
  }

  const formData = new FormData();
  formData.append("file", file);

  try {

    dispatch(responseAction.setLoading());

    const res = await axios.post(
      BASE_BACKEND_API + "upload/csv",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );

    // BACKEND RETURNS ANALYTICS JSON
    
    const response = res.data;
    console.log("Analytics response:", response);
    const flagedTransactionDetails = [];

    response.results.forEach(ele => {
      flagedTransactionDetails.push({
        transactionId: ele.transaction_id,
        userId: ele.user_id,
        amount: ele.amount,
        location: ele.location,
        explaination: ele.risk_assessment, 
        isFraud: ele.is_fraud,
        anomalyPercentage: ele.anomaly_pct
      })
    });

    const newAction = {
      totalTransactions: response.summary.total_transactions,
      activeAnomalies: response.summary.flagged_transactions,
      avgTransactionAmount: response.metrics.average_transaction_amount,
      topRiskRegion: response.metrics.top_risk_region,
      anomalyPercentageAvg: response.summary.anomaly_pct_avg,
      fraudTransationDistribution: response.fraud_type_distribution,
      flagedTransactionDetails: flagedTransactionDetails,
    }

    dispatch(responseAction.setResponse(newAction))

  } catch (err) {
    console.error("Upload failed:", err);
    throw err;
  }
});

const responseSlice = createSlice({
  name: "response",
  initialState: {
    isLoading: false,
    totalTransactions: -1,
    activeAnomalies: -1,
    avgTransactionAmount: -1,
    topRiskRegion: "Place",
    anomalyPercentageAvg: 0.0,
    fraudTransationDistribution: {},
    flagedTransactionDetails: [{
      transactionId: "",
      userId: "",
      amount: -1,
      location: "",
      explaination: "",
      isFraud: false,
      anomalyPercentage: 0.0,
    }]
  },
  reducers: {
    setResponse(state, action)
    {
      return {...action.payload, isLoading: false};
    },
    setLoading(state)
    {
      state.isLoading = true;
    }
  }
})

export const responseAction = responseSlice.actions;

const store = configureStore({
  reducer:{
    response: responseSlice.reducer,
  }
})

export default store