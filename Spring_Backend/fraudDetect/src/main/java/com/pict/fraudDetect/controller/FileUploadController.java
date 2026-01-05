package com.pict.fraudDetect.controller;

import com.pict.fraudDetect.model.AnalysisResponseDTO;
import com.pict.fraudDetect.service.MlCsvProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private MlCsvProxyService mlCsvProxyService;

    @PostMapping("/csv")
    public ResponseEntity<AnalysisResponseDTO> uploadCsv(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        AnalysisResponseDTO response =
                mlCsvProxyService.processCsv(file);

        return ResponseEntity.ok(response);
    }
}
