package com.pict.fraudDetect.ml;

import com.pict.fraudDetect.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MlPredictionService {

    @Autowired
    private MlModelClient mlModelClient;

    public MlResult evaluateTransaction(Transaction transaction) {
        return mlModelClient.predict(transaction);
    }
}
