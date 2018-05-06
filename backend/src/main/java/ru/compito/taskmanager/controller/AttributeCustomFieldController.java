package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.service.CustomFieldService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.ATTRIBUTE_PATH)
public class AttributeCustomFieldController {
    @Autowired
    private CustomFieldService customFieldService;

    @GetMapping(value = "/{attributeId}/customfields/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getCustomFields(@PathVariable Integer attributeId) {
        List<CustomField> customFields = customFieldService.findByAttributeId(attributeId);
        return new ResponseEntity<>(customFields, HttpStatus.OK);
    }

    @GetMapping(value = "/{attributeId}/customfields/{customFieldId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CustomField> getAttribute(@PathVariable Integer attributeId, @PathVariable Integer customFieldId) {
        CustomField customField = customFieldService.getByAttributeAndCustomFieldId(attributeId,customFieldId);
        if(customField==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customField, HttpStatus.OK);
    }

}
