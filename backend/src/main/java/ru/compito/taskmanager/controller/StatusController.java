package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.TaskStatus;
import ru.compito.taskmanager.service.TaskStatusService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.STATUS_PATH)
public class StatusController {

    @Autowired
    TaskStatusService taskStatusService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskStatus> getAll() {
        return taskStatusService.findAll();
    }
    @GetMapping(value = "/{taskStatusId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskStatus getOne(@PathVariable Integer taskStatusId) {
        return taskStatusService.getOne(taskStatusId);
    }
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatus createTaskStatus(@RequestBody TaskStatus taskStatus) {
        return taskStatusService.save(taskStatus);
    }
    @PutMapping(value = "/{taskStatusId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable Integer taskStatusId, @RequestBody TaskStatus taskStatus) {
        taskStatusService.update(taskStatus);
    }
    @DeleteMapping("/{taskStatusId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer taskStatusId) {
        taskStatusService.delete(taskStatusId);
    }
}
