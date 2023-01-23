package com.ewave.xmlupload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewave.xmlupload.entities.Record;
import com.ewave.xmlupload.services.RecordsService;

@RestController
@RequestMapping(value = "/api/v1/records", produces = { "application/json" })
@CrossOrigin("*")
public class RecordsController {
    
    @Autowired
    private RecordsService recordsService;

    @GetMapping("/region/{name}")
    public ResponseEntity<Page<Record>> getDataByRegion(@PathVariable("name") String regionName, Pageable pageable) {
        Page<Record> records = this.recordsService.getRecordsByRegion(regionName, pageable);

        return ResponseEntity.ok(records);
    }
}
