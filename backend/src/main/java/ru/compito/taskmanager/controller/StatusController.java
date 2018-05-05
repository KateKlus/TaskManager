package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<TaskStatus> taskStatuses = taskStatusService.findAll();
        return new ResponseEntity<>(taskStatuses, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskStatusId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<TaskStatus> getOne(@PathVariable Integer taskStatusId) {
        TaskStatus taskStatus = taskStatusService.getOne(taskStatusId);
        if(taskStatus==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(taskStatus, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<TaskStatus> createTaskStatus(@RequestBody TaskStatus taskStatus) {
        TaskStatus newTaskStatus = taskStatusService.save(taskStatus);
        return new ResponseEntity<>(newTaskStatus, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{taskStatusId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<TaskStatus> update(@PathVariable Integer taskStatusId, @RequestBody TaskStatus taskStatus) {
        TaskStatus updatedTaskStatus = taskStatusService.update(taskStatus);
        return new ResponseEntity<>(updatedTaskStatus, HttpStatus.OK);
    }
    @DeleteMapping("/{taskStatusId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer taskStatusId) {
        taskStatusService.delete(taskStatusId);
    }
}
