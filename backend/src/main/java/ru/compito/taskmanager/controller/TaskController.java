package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.service.CustomFieldService;
import ru.compito.taskmanager.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.TASK_PATH)
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CustomFieldService customFieldService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @GetMapping(value = "/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getOne(@PathVariable Integer taskId) {
        return taskService.getOne(taskId);
    }

    @PutMapping(value = "/{taskId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Task update(@PathVariable Integer taskId, @RequestBody Task task) {
        return taskService.update(task);
    }

    @DeleteMapping("/{taskId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer taskId) {
        taskService.delete(taskId);
    }

    @GetMapping(value = "/{taskId}/customfields/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomField> getAllCustomFields(@PathVariable Integer taskId) {
        return customFieldService.getAllCustomFieldsByTask(taskId);
    }
}
