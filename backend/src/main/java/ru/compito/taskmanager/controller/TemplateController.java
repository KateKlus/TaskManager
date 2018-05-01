package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public List<TaskTemplate> getAll() {
        return taskTemplateService.findAll();
    }

    @GetMapping(value = "/{taskTemplateId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskTemplate getOne(@PathVariable Integer taskTemplateId) {
        return taskTemplateService.getOne(taskTemplateId);
    }

    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskTemplate create(@RequestBody TaskTemplate taskTemplate) {
        return taskTemplateService.save(taskTemplate);
    }

    @PutMapping(value = "/{taskTemplateId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskTemplate update(@PathVariable Integer taskTemplateId, @RequestBody TaskTemplate updatedTaskTemplate) {
        return taskTemplateService.update(taskTemplateId,updatedTaskTemplate);
    }

    @DeleteMapping("/{taskTemplateId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer taskTemplateId) {
        taskTemplateService.delete(taskTemplateId);
    }


}
