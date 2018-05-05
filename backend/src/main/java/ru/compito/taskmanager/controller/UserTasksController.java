package ru.compito.taskmanager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.service.ContentRelatedRoleService;
import ru.compito.taskmanager.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.USER_PATH)
public class UserTasksController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;

    @GetMapping(value = "/{userId}/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getTasks(@PathVariable Integer userId) {
        List<Task> tasks = taskService.findByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/tasks/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> getTask(@PathVariable Integer userId, @PathVariable Integer taskId) {
        Task task = taskService.getTaskByUserId(userId,taskId);
        if(task==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @PostMapping(value = "/{userId}/tasks/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> createTask(@PathVariable Integer userId, @RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer boardId = task.getBoard().getId();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)||
                contentRelatedRoleService.isContentModerator(boardId,authentication)||
                contentRelatedRoleService.isContentDeveloper(boardId,authentication)) {
            Task newTask = taskService.save(userId, task);
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}/tasks/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTasks(@PathVariable Integer id) {
        taskService.deleteAllForUser(id);
    }
}
