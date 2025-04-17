package com.example.lab1.controller;

import com.example.lab1.dto.SignatureScanResult;
import com.example.lab1.service.FileScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileScanController {
    private final FileScanService fileScanService;

    @PostMapping(path="/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<SignatureScanResult> upload(@RequestParam("file") MultipartFile file) throws Exception{
        return fileScanService.scan(file);
    }
}
