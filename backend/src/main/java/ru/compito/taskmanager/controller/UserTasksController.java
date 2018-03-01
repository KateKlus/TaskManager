package ru.compito.taskmanager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserTasksController {

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{username}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasks(@PathVariable String username) {
        return taskService.findByUsername(username);
    }

    @DeleteMapping("/{id}/tasks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTasks(@PathVariable Integer id) {
        taskService.deleteAllForUser(id);
    }
}
