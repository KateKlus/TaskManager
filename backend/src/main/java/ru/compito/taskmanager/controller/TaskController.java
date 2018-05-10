package ru.compito.taskmanager.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.ContentRelatedRoleService;
import ru.compito.taskmanager.service.CustomFieldService;
import ru.compito.taskmanager.service.TaskService;
import ru.compito.taskmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.TASK_PATH)
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomFieldService customFieldService;
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;
    @Autowired

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<Task> tasks =  taskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> getOne(@PathVariable Integer taskId) {
        Task task = taskService.getOne(taskId);
        if(task==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskId}/users/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getUsersByTask(@PathVariable Integer taskId) {
        List<User> users = userService.findByTaskId(taskId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskId}/customfields/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAllCustomFields(@PathVariable Integer taskId) {
        List<CustomField> customFields = customFieldService.getAllCustomFieldsByTask(taskId);
        return new ResponseEntity<>(customFields, HttpStatus.OK);
    }

    @PostMapping(value = "/{taskId}/users/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> addUser(@PathVariable Integer taskId, @RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User author = userService.findByUsername(authentication.getName());
        Task task = taskService.getOne(taskId);
        if(author.equals(task.getAuthor())) {
            taskService.addUser(task,user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/{taskId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> update(@PathVariable Integer taskId, @RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer boardId = task.getBoard().getId();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)||
                contentRelatedRoleService.isContentModerator(boardId,authentication)||
                contentRelatedRoleService.isContentDeveloper(boardId,authentication)) {
            Task updateTask = taskService.update(task);
            return new ResponseEntity<>(updateTask,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{taskId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer taskId) {
        Task task = taskService.getOne(taskId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer boardId = task.getBoard().getId();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)||
                contentRelatedRoleService.isContentModerator(boardId,authentication)) {
            taskService.delete(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
