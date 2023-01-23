package com.ewave.xmlupload.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ewave.xmlupload.entities.Record;
import com.ewave.xmlupload.repositories.RecordsRepository;

@Service
public class RecordsService {

    @Autowired
    private RecordsRepository recordsRepository;
    
    public List<Record> createRecords(List<Record> records) {
        return this.recordsRepository.saveAll(records);
    }

    public Page<Record> getRecordsByRegion(String region, Pageable pageable) {
        return this.recordsRepository.findRecordByRegion(region, pageable);
    }

}
