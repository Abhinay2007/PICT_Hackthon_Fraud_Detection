package com.pict.fraudDetect.service;

import com.pict.fraudDetect.model.Anomaly;
import java.util.List;

public interface AnomalyEngineService {
    void runAnomalyDetection();
    List<Anomaly> getAllAnomalies();
}
