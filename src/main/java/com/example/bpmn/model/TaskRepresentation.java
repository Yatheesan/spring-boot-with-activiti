package com.example.bpmn.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRepresentation {
    private String id;
    private String name;
    private String processInstanceId;

    public TaskRepresentation(String id, String name, String processInstanceId) {
        this.id = id;
        this.name = name;
        this.processInstanceId = processInstanceId;
    }
}
