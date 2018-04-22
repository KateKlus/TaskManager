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
@RequestMapping(value = ServiceConstants.ATTRIBUTES)
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Attribute> getAll() {
        return attributeService.findAll();
    }

    @GetMapping(value = "/{attributeId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Attribute getOne(@PathVariable Integer attributeId) {
        return attributeService.getOne(attributeId);
    }

    @PutMapping(value = "/{attributeId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Attribute update(@PathVariable Integer attributeId, @RequestBody Attribute updatedAttribute) {
        return attributeService.update(attributeId, updatedAttribute);
    }

    @DeleteMapping("/{attributeId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer attributeId) {
        attributeService.delete(attributeId);
    }
}
