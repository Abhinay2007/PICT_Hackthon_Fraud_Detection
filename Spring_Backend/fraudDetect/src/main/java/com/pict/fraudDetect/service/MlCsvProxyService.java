package com.pict.fraudDetect.service;

import com.pict.fraudDetect.model.AnalysisResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MlCsvProxyService {

    @Autowired
    private WebClient webClient;

    private static final String ML_URL = "http://localhost:8001/predict_csv";
    private static final String LLM_URL = "http://localhost:8001/analyze-csv";

    public AnalysisResponseDTO processCsv(MultipartFile file) {

        try {
//            /* ------------------ STEP 1: SEND TO ML ------------------ */
            ByteArrayResource csvResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            MultiValueMap<String, Object> mlBody = new LinkedMultiValueMap<>();
            mlBody.add("file", csvResource);

            byte[] predictedCsv = webClient.post()
                    .uri(ML_URL)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(mlBody)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();

            /* ------------------ STEP 2: SEND TO LLM ------------------ */
            ByteArrayResource predictedCsvResource = new ByteArrayResource(predictedCsv) {
                @Override
                public String getFilename() {
                    return "predicted_output.csv";
                }
            };

            MultiValueMap<String, Object> llmBody = new LinkedMultiValueMap<>();
            llmBody.add("file", predictedCsvResource);

            return webClient.post()
                    .uri(LLM_URL)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(llmBody)
                    .retrieve()
                    .bodyToMono(AnalysisResponseDTO.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("ML → LLM pipeline failed", e);
        }
    }
}
