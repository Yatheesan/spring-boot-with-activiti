package com.example.bpmn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private String price;
    private String location;
}
