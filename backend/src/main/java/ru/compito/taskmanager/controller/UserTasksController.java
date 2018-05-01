package ru.compito.taskmanager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.USER_PATH)
public class UserTasksController {

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{userId}/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasks(@PathVariable Integer userId) {
        return taskService.findByUserId(userId);
    }

    @GetMapping(value = "/{userId}/tasks/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getTask(@PathVariable Integer userId, @PathVariable Integer taskId) {
        return taskService.getTaskByUserId(userId,taskId);
    }
    @PostMapping(value = "/{userId}/tasks/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@PathVariable Integer userId, @RequestBody Task task) {
        return taskService.save(userId, task);
    }

    @DeleteMapping("/{id}/tasks/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTasks(@PathVariable Integer id) {
        taskService.deleteAllForUser(id);
    }
}
