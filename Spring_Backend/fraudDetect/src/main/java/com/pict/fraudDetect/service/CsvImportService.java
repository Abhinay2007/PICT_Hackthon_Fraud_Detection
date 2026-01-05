package com.pict.fraudDetect.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvImportService {
    void importCsv(MultipartFile file) throws Exception;
}
