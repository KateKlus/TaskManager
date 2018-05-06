package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.TASKTEMPLATE_PATH)
public class TaskTemplateController {

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{taskTemplateId}/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getTasksByTaskTemplateId(@PathVariable Integer taskTemplateId) {
        List<Task> tasks = taskService.findByTaskTemplateId(taskTemplateId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskTemplateId}/tasks/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> getTask(@PathVariable Integer taskTemplateId, @PathVariable Integer taskId) {
        Task task = taskService.getByTaskTemplateAndTaskId(taskTemplateId,taskId);
        if(task==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(task, HttpStatus.OK);
    }

}
