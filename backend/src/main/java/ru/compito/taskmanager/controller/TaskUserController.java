package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.TaskService;
import ru.compito.taskmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{id}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(@PathVariable Integer id) {
        return userService.findByTaskId(id);
    }

    @PostMapping(value = "/users/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@PathVariable Integer id, @RequestBody Task task) {
        return taskService.save(id, task);
    }
}
