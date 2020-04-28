package com.example.bpmn.process;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessService {
    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    public String StartProcess(String processName){
        return "";
    }
}
