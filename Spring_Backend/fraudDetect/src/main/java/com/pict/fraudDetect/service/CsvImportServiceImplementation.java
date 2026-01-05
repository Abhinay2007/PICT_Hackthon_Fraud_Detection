package com.pict.fraudDetect.service;

import com.pict.fraudDetect.model.Transaction;
import com.pict.fraudDetect.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class CsvImportServiceImplementation implements CsvImportService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AnomalyEngineService anomalyEngineService;

    @Override
    public void importCsv(MultipartFile file) throws Exception {

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
                )
        ) {
            String line;

            // Skip header
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",", -1); // keep empty columns

                // Minimum safety check
                if (data.length < 5) {
                    System.err.println("Invalid row (too short): " + line);
                    continue;
                }

                try {
                    String transactionId = data[0].trim();
                    String userId = data[1].trim();

                    // IMPORTANT FIX: amount is at index 3, NOT 2
                    double amount = Double.parseDouble(data[3].trim());

                    String location = data[4].trim();

                    // CSV has no datetime → generate one
                    LocalDateTime timestamp = LocalDateTime.now();

                    Transaction txn = new Transaction(
                            transactionId,
                            userId,
                            amount,
                            location,
                            timestamp
                    );

                    transactionRepository.save(txn);

                } catch (Exception rowEx) {
                    System.err.println("Failed to parse row:");
                    System.err.println(line);
                    rowEx.printStackTrace();
                }
            }
        }

        // Run anomaly detection AFTER import
        anomalyEngineService.runAnomalyDetection();
    }
}
