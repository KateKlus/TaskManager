package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.service.TaskTemplateService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.TASKTEMPLATE_PATH)
public class TemplateController {

    @Autowired
    private TaskTemplateService taskTemplateService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<TaskTemplate> taskTemplates = taskTemplateService.findAll();
        return new ResponseEntity<>(taskTemplates, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskTemplateId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<TaskTemplate> getOne(@PathVariable Integer taskTemplateId) {
        TaskTemplate taskTemplate = taskTemplateService.getOne(taskTemplateId);
        return new ResponseEntity<>(taskTemplate, HttpStatus.OK);
    }

    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<TaskTemplate> create(@RequestBody TaskTemplate taskTemplate) {
        TaskTemplate newTaskTemplate = taskTemplateService.save(taskTemplate);
        return new ResponseEntity<>(newTaskTemplate, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{taskTemplateId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<TaskTemplate> update(@PathVariable Integer taskTemplateId, @RequestBody TaskTemplate updatedTaskTemplate) {
        TaskTemplate taskTemplate = taskTemplateService.update(taskTemplateId,updatedTaskTemplate);
        return new ResponseEntity<>(taskTemplate, HttpStatus.OK);

    }

    @DeleteMapping("/{taskTemplateId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer taskTemplateId) {
        taskTemplateService.delete(taskTemplateId);
    }


}
