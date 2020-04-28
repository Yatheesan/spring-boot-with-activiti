package com.example.bpmn.controllers;

import com.example.bpmn.model.TaskRepresentation;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/process")
public class ProcessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    public ProcessController(){ }

    @PostMapping("/start")
    public String startInventoryProcess(@RequestBody String processName){

        if (processName.equalsIgnoreCase("inventory")) {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("productName","po1");
            variables.put("productId", "123");
            variables.put("person", "john");

            ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                    .start()
                    .withProcessDefinitionKey("inventoryProcess")
                    .withName("inventoryProcess")
                    .withVariables(variables)
                    .build());
            String message = ">>> Created Process Instance: " + processInstance;
            System.out.println(message);
            return "Inventory Process Started with process id : " + processInstance.getId() + " Status: " + processInstance.getStatus();
        }
        return "Invalid process name";
    }

    @GetMapping("/get-tasks/{processInstanceId}")
    public List<TaskRepresentation> getTasks(@PathVariable String processInstanceId) {

        List<Task> usertasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        return usertasks.stream()
                .map(task -> new TaskRepresentation(
                        task.getId(), task.getName(), task.getProcessInstanceId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/complete-task-by-process/{processInstanceId}")
    public String completeTaskByProcessId(@PathVariable String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        taskService.complete(task.getId());
        return "Task Completed for processId - " + processInstanceId + " task id - " + task.getId();
    }

    @GetMapping("/complete-task-by-name/{taskName}")
    public String completeTaskByTaskId(@PathVariable String taskName) {
        Task task = taskService.createTaskQuery().taskName(taskName).singleResult();
        taskService.complete(task.getId());
        return "Task Completed for task id : " + task.getId();
    }

    @GetMapping("/{processInstanceId}/status")
    public String processStatus(@PathVariable String processInstanceId) {
        ProcessInstance processInstance = processRuntime.processInstance(processInstanceId);
        return "Status: " + processInstance.getStatus();
    }
}
