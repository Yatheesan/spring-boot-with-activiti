package com.example.bpmn.inbound;

import com.example.bpmn.model.ASN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InBoundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InBoundService.class);

    private ASNRepository asnRepository;

    @Autowired
    public InBoundService(ASNRepository asnRepository){
        this.asnRepository = asnRepository;
    }
    public void generateInBoundService(){
        List<ASN> asns = new ArrayList<>();
        asns.add(new ASN("123","milk"));
        asns.add(new ASN("456","food"));
        asns.add(new ASN("789","water"));
        asns.add(new ASN("741","soap"));
        asns.add(new ASN("852","phone"));
        LOGGER.info("Added ASN Details to the system : [milk,food,water,soap,phone]");
        asnRepository.saveAll(asns);
    }
}
