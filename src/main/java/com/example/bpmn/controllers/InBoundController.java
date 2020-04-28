package com.example.bpmn.controllers;

import com.example.bpmn.model.Product;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inbound")
public class InBoundController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InBoundController.class);

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/start")
    public String startInBoundProcess(@RequestBody String asnName){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("asnName",asnName);
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("InBoundManagement")
                .withVariables(variables)
                .build());
        String message = ">>> Created Process Instance: " + processInstance;
        LOGGER.info(message);
        LOGGER.info("Number of Tasks : " + this.taskService.createTaskQuery().count());
        List<Task> tasks = this.taskService.createTaskQuery().processDefinitionKey("InBoundManagement").list();
        return "Inventory Process Started with process id : " + processInstance.getId()
                + ",Next Task : " + tasks.get(0).getName() + ", ID: " +  tasks.get(0).getId() ;
    }

    @PostMapping("/{taskId}/scan-product")
    public String scanProduct(@RequestBody Product product, @PathVariable String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null){
            return "Invalid task id";
        }
        taskService.complete(task.getId());
        LOGGER.info("Task completed: "+ task.getId());
        Task nextTask = taskService.createTaskQuery().processDefinitionKey("InBoundManagement").list().get(0);
        LOGGER.info("Next Task: "+ nextTask.getName());
        return "Task Completed for task id : " + task.getId() + "Next Task: "+ nextTask.getName() + "Id: " + nextTask.getId();
    }

    @PostMapping("/{taskId}/put-away")
    public String putAwayProduct(@RequestBody String location, @PathVariable String taskId){
        LOGGER.info("Selected location: "+ location);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null){
            return "Invalid task id";
        }
        taskService.complete(task.getId());
        LOGGER.info("Put Away Task completed: "+ task.getId());
        List<Task> nextTask = taskService.createTaskQuery().processDefinitionKey("InBoundManagement").list();
        if (nextTask.isEmpty()){
            return "Task Completed for task id : " + task.getId();
        }
        LOGGER.info("Next Task: "+ nextTask.get(0).getName());
        return "Task Completed for task id : " + task.getId() + "Next Task: "+ nextTask.get(0).getName() + "Id: " + nextTask.get(0).getId();
    }
}
