package com.example.bpmn.inventory;

import com.example.bpmn.model.Product;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private ProcessRuntime processRuntime;

    private ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public String StartInventoryProcess(){
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("inventoryProcess")
                .withName("inventoryProcess")
                .build());
        String message = ">>> Created Process Instance: " + processInstance;
        System.out.println(message);
        return "Inventory Process Started with process id : " + processInstance.getId() + " Status: " + processInstance.getStatus();
    }

    public String addInventory(List<Product> products){
        this.productRepository.saveAll(products);
        return "Inventory got added";
    }
}
