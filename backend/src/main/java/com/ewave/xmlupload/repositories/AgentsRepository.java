package com.ewave.xmlupload.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewave.xmlupload.entities.Agent;

public interface AgentsRepository extends JpaRepository<Agent, Long>{
    
    Agent findByCode(int agentCode);
    
}
