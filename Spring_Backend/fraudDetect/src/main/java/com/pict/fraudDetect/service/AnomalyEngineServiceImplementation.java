package com.pict.fraudDetect.service;

import com.pict.fraudDetect.dto.ExplanationRequestDTO;
import com.pict.fraudDetect.ml.MlResult;
import com.pict.fraudDetect.ml.MlPredictionService;
import com.pict.fraudDetect.model.Anomaly;
import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.model.UserStats;
import com.pict.fraudDetect.repository.AnomalyRepository;
import com.pict.fraudDetect.repository.TransactionRepository;
import com.pict.fraudDetect.rule.AnomalyRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnomalyEngineServiceImplementation implements AnomalyEngineService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AnomalyRepository anomalyRepository;

    @Autowired
    private UserStatsService userStatsService;

    @Autowired
    private List<AnomalyRule> anomalyRules;

    @Autowired
    private MlPredictionService mlPredictionService;

    @Autowired
    private LangChainClientService langChainClientService;

    @Override
    public void runAnomalyDetection() {

        List<Transaction> transactions = transactionRepository.findAll();

        for (Transaction txn : transactions) {

            UserStats stats =
                    userStatsService.calculateUserStats(txn.getUserId());

            List<String> anomalyTypes = new ArrayList<>();
            List<String> reasons = new ArrayList<>();

            // 1️⃣ Rule‑based detection
            for (AnomalyRule rule : anomalyRules) {
                if (rule.isAnomalous(txn, stats)) {

                    anomalyTypes.add(rule.getType());
                    reasons.add(rule.getReason(txn, stats));

                    Anomaly anomaly = new Anomaly(
                            txn.getTransactionId(),
                            rule.getType(),
                            rule.getReason(txn, stats)
                    );
                    anomalyRepository.save(anomaly);
                }
            }

            // 2️⃣ ML prediction
            MlResult mlResult =
                    mlPredictionService.evaluateTransaction(txn);

            // 3️⃣ CREATE ExplanationRequestDTO ✅ (THIS WAS MISSING)
            ExplanationRequestDTO request = new ExplanationRequestDTO(
                            txn.getTransactionId(),
                            anomalyTypes,
                            reasons,
                            mlResult.getRiskScore()
                    );

            // 4️⃣ Call LangChain for explanation
            langChainClientService.explain(request);
        }
    }

    @Override
    public List<Anomaly> getAllAnomalies() {
        return anomalyRepository.findAll();
    }
}

