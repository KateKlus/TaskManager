package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.entity.TaskStatus;
import ru.compito.taskmanager.service.TaskStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    @Autowired
    TaskStatusService taskStatusService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskStatus> getAll() {
        return taskStatusService.findAll();
    }
    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskStatus getOne(@PathVariable Integer id) {
        return taskStatusService.getOne(id);
    }
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatus createTask(@RequestBody TaskStatus taskStatus) {
        return taskStatusService.save(taskStatus);
    }
    @PutMapping(value = "/{id}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable Integer id, @RequestBody TaskStatus taskStatus) {
        taskStatusService.update(taskStatus);
    }
    @DeleteMapping("/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        taskStatusService.delete(id);
    }
}
