package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.service.TaskService;
import ru.compito.taskmanager.service.TaskTemplateService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.TASKTEMPLATE_PATH)
public class TaskTemplateController {
    @Autowired
    private TaskTemplateService taskTemplateService;
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{taskTemplateId}/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasksByTaskTemplateId(@PathVariable Integer taskTemplateId) {
        return taskService.findByTaskTemplateId(taskTemplateId);
    }

    @GetMapping(value = "/{taskTemplateId}/tasks/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getTask(@PathVariable Integer taskTemplateId, @PathVariable Integer taskId) {
        return taskService.getByTaskTemplateAndTaskId(taskTemplateId,taskId);
    }

}
