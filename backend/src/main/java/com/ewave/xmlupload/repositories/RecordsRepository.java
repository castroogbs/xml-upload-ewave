package com.ewave.xmlupload.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ewave.xmlupload.entities.Record;

public interface RecordsRepository extends JpaRepository<Record, Long> {

    // @Query("SELECT r FROM Record r WHERE r.region LIKE %?1%")
    Page<Record> findRecordByRegion(String region, Pageable pageable);

}
