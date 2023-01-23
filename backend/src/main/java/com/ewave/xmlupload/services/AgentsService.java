package com.ewave.xmlupload.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewave.xmlupload.entities.Agent;
import com.ewave.xmlupload.repositories.AgentsRepository;

@Service
public class AgentsService {

    @Autowired
    private AgentsRepository agentsRepository;

    public Agent findByCode(int agentCode) {
        Agent a = this.agentsRepository.findByCode(agentCode);
        return a;
    }

    public Agent create(int agentCode) {
        Agent a = new Agent();
        a.setId(UUID.randomUUID());
        a.setCode(agentCode);
        return this.agentsRepository.save(a);
    }
}
