package ru.compito.taskmanager.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<Task> tasks =  taskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> getOne(@PathVariable Integer taskId) {
        Task task = taskService.getOne(taskId);
        return new ResponseEntity<>(task,HttpStatus.OK);
    }

    @PutMapping(value = "/{taskId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Task> update(@PathVariable Integer taskId, @RequestBody Task task) {
        Task updateTask = taskService.update(task);
        return new ResponseEntity<>(updateTask,HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer taskId) {
        taskService.delete(taskId);
    }

    @GetMapping(value = "/{taskId}/customfields/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAllCustomFields(@PathVariable Integer taskId) {
        List<CustomField> customFields = customFieldService.getAllCustomFieldsByTask(taskId);
        return new ResponseEntity<>(customFields, HttpStatus.OK);
    }
}
