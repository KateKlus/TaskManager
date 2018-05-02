package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.service.AttributeService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.TASKTEMPLATE_PATH)
public class TemplateAttributesController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping(value = "/{taskTemplateId}/attributes/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Attribute> getAttributes(@PathVariable Integer taskTemplateId) {
        return attributeService.findByTaskTemplateId(taskTemplateId);
    }

    @GetMapping(value = "/{taskTemplateId}/attributes/{attributeId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Attribute getAttribute(@PathVariable Integer taskTemplateId, @PathVariable Integer attributeId) {
        return attributeService.getByTaskTemplateAndAttributeId(taskTemplateId,attributeId);
    }
    @PostMapping(value = "/{taskTemplateId}/attributes/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Attribute createAttribute(@PathVariable Integer taskTemplateId, @RequestBody Attribute attribute) {
        return attributeService.save(taskTemplateId, attribute);
    }


    @DeleteMapping("/{taskTemplateId}/attributes/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttributes(@PathVariable Integer taskTemplateId) {
        attributeService.deleteAllForTaskTemplate(taskTemplateId);
    }
}
