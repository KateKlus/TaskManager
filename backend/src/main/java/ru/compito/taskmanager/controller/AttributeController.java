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
import ru.compito.taskmanager.service.AttributeService;
import ru.compito.taskmanager.service.ContentRelatedRoleService;
import ru.compito.taskmanager.service.CustomFieldService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.ATTRIBUTE_PATH)
public class AttributeController {

    @Autowired
    private AttributeService attributeService;
    @Autowired
    private CustomFieldService customFieldService;
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<Attribute> attributes = attributeService.findAll();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping(value = "/{attributeId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Attribute> getOne(@PathVariable Integer attributeId) {
        Attribute attribute = attributeService.getOne(attributeId);
        if(attribute==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(attribute, HttpStatus.OK);
    }

    @PutMapping(value = "/{attributeId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Attribute> update(@PathVariable Integer attributeId, @RequestBody Attribute updatedAttribute) {
        CustomField customField = customFieldService.getByAttribute(attributeId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Attribute attribute = attributeService.update(attributeId, updatedAttribute);
        return new ResponseEntity<>(attribute, HttpStatus.OK);

    }

    @DeleteMapping("/{attributeId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer attributeId) {
        CustomField customField = customFieldService.getByAttribute(attributeId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        attributeService.delete(attributeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
