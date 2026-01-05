package com.pict.fraudDetect.controller;

import com.pict.fraudDetect.model.Anomaly;
import com.pict.fraudDetect.service.AnomalyEngineServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anomalies")
public class AnomalyController {

    @Autowired
    private AnomalyEngineServiceImplementation anomalyEngineService;

    // Get all detected anomalies
    @GetMapping
    public List<Anomaly> getAllAnomalies() {
        return anomalyEngineService.getAllAnomalies();
    }

    // Trigger anomaly detection manually (optional for hackathon)
    @PostMapping("/run")
    public String runAnomalyDetection() {
        anomalyEngineService.runAnomalyDetection();
        return "Anomaly detection executed successfully";
    }
}
