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
@RequestMapping(value = ServiceConstants.USER_PATH)
public class UserTasksController {

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{userId}/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getTasks(@PathVariable Integer userId) {
        List<Task> tasks = taskService.findByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/tasks/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> getTask(@PathVariable Integer userId, @PathVariable Integer taskId) {
        Task task = taskService.getTaskByUserId(userId,taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @PostMapping(value = "/{userId}/tasks/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> createTask(@PathVariable Integer userId, @RequestBody Task task) {
        Task newTask = taskService.save(userId, task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/tasks/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTasks(@PathVariable Integer id) {
        taskService.deleteAllForUser(id);
    }
}
