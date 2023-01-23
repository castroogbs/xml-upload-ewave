package com.ewave.xmlupload.services;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ewave.xmlupload.entities.Agent;
import com.ewave.xmlupload.entities.Record;

@Service
public class XmlParserService {

    @Autowired
    private AgentsService agentsService;

    @Autowired
    private RecordsService recordsService;

    private List<Record> records = new ArrayList<>();

    private Agent agent;

    private String recordsDate;
    
    private String recordsRegion;

    public boolean parseFile(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();
            NodeList agents = document.getElementsByTagName("agente");
                        
            this.readNodes(agents);
            List<Record> savedRecords = this.recordsService.createRecords(this.records);

            if(savedRecords != null) {
                return true;
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void readNodes(NodeList agentsList) {        
        for (int c = 0; c < agentsList.getLength(); c++) {
            Node agent = agentsList.item(c);
            NodeList agentChildsList = agent.getChildNodes();

            for (int i = 0; i < agentChildsList.getLength(); i++) {
                Node _node = agentChildsList.item(i);
    
                if(_node.getNodeType() == Node.ELEMENT_NODE) {
                    Element _element = (Element) _node;
                    String _nodeName = _node.getNodeName();
                    
                    if(_nodeName == "codigo") {
                        int aCode = Integer.parseInt(_element.getTextContent());
                        Agent a = this.getAgentByCode(aCode);
                        this.agent = a;
                        
                        System.out.println("Agente: " + aCode);
                    }
    
                    if(_nodeName == "data") {
                        this.recordsDate = _element.getTextContent();
                    }
    
                    if(_nodeName == "regiao") {
                        this.recordsRegion = _element.getAttribute("sigla");
                        NodeList detailsList = _node.getChildNodes();
                        
                        for (int j = 0; j < detailsList.getLength(); j++) {
                            Node detailNode = detailsList.item(j);
    
                            if(_node.getNodeType() == Node.ELEMENT_NODE) {
                                String detailNodeName = detailNode.getNodeName();
    
                                if(detailNodeName == "geracao" || detailNodeName == "compra") {
                                    this.addRecord(detailNodeName, detailNode, this.agent, this.recordsRegion, this.recordsDate);
                                }
                            }
                        }
                    }
    
                }
            }
        }
    }

    private void addRecord(String recordType, Node node, Agent curAgent, String region, String date) {
        NodeList nodeChildsList = node.getChildNodes();
        for (int i = 0; i < nodeChildsList.getLength(); i++) {
            Node _node = nodeChildsList.item(i);

            if(_node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) _node;
                Boolean isGeneration = recordType == "geracao" ? true : false;
                Boolean isPurchase = !isGeneration;
                
                String value = element.getTextContent();

                Record record = new Record();
                record.setId(UUID.randomUUID());
                record.setGeneration(isGeneration);
                record.setPurchase(isPurchase);
                record.setRecordDate(this.getDateFromISO(date));
                record.setRegion(region);
                record.setValue(Float.parseFloat(value));
                record.setAgent(curAgent);
                this.records.add(record);
            }
        }
    }

    private Date getDateFromISO(String datestring) {    
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        TemporalAccessor accessor = timeFormatter.parse(datestring);

        Date date = Date.from(Instant.from(accessor));     

        return date;
      }

    private Agent getAgentByCode(int agentCode) {
        Agent a = this.agentsService.findByCode(agentCode);
                        
        if (a == null) {
            Agent newAgent = this.agentsService.create(agentCode);
            return newAgent;
        }

        return a;
    }

}
