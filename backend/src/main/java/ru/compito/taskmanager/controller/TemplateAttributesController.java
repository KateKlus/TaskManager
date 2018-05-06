package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.service.AttributeService;
import ru.compito.taskmanager.service.ContentRelatedRoleService;
import ru.compito.taskmanager.service.TaskService;
import ru.compito.taskmanager.service.TaskTemplateService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.TASKTEMPLATE_PATH)
public class TemplateAttributesController {

    @Autowired
    private AttributeService attributeService;
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/{taskTemplateId}/attributes/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAttributes(@PathVariable Integer taskTemplateId) {
        List<Attribute> attributes = attributeService.findByTaskTemplateId(taskTemplateId);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping(value = "/{taskTemplateId}/attributes/{attributeId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Attribute> getAttribute(@PathVariable Integer taskTemplateId, @PathVariable Integer attributeId) {
        Attribute attribute = attributeService.getByTaskTemplateAndAttributeId(taskTemplateId,attributeId);
        if(attribute==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(attribute, HttpStatus.OK);
    }
    @PostMapping(value = "/{taskTemplateId}/attributes/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Attribute> createAttribute(@PathVariable Integer taskTemplateId, @RequestBody Attribute attribute) {
        Task task  = taskService.getByTaskTemplateId(taskTemplateId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Attribute newAttribute = attributeService.save(taskTemplateId, attribute);
            return new ResponseEntity<>(newAttribute, HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskTemplateId}/attributes/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody ResponseEntity<?> deleteAttributes(@PathVariable Integer taskTemplateId) {
        Task task  = taskService.getByTaskTemplateId(taskTemplateId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        attributeService.deleteAllForTaskTemplate(taskTemplateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
