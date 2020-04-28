package com.example.bpmn.controllers;

import com.example.bpmn.inventory.InventoryService;
import com.example.bpmn.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @PostMapping("/start")
    public String inventoryProcessStart(){
        return this.inventoryService.StartInventoryProcess();
    }

    @PostMapping("/add-item")
    public String addNewItems(@RequestBody List<Product> products){
        LOGGER.info("Add Inventory Item Called");
        return this.inventoryService.addInventory(products);
    }
}
