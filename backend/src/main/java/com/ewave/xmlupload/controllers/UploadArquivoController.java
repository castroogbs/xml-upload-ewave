package com.ewave.xmlupload.controllers;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ewave.xmlupload.services.UploadFileService;
import com.ewave.xmlupload.services.XmlParserService;

@RestController
@RequestMapping(value = "/api/v1/upload", produces = { "application/json" })
@CrossOrigin("*") // enable cors
public class UploadArquivoController {
    
    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private XmlParserService xmlParserService;

    @PostMapping("/file")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            Path filePath = this.uploadFileService.uploadFile(file);
            Boolean isFileParsed = this.xmlParserService.parseFile(filePath.toString());
            if(isFileParsed) {
                this.uploadFileService.deleteFile(filePath);
            }
            return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"File has been processed successfully.\" }");
        } catch(IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"message\": \"Could not proceed with the upload.\" }");
        }
    }
}
