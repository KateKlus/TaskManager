package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.service.CustomFieldService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.CUSTOMFIELD_PATH)
public class CustomFieldController {

    @Autowired
    private CustomFieldService customFieldService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomField> getAll() {
        return customFieldService.findAll();
    }

    @GetMapping(value = "/{customFieldId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomField getOne(@PathVariable Integer customFieldId) {
        return customFieldService.getOne(customFieldId);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomField createCustomField(@RequestBody CustomField customField) {
        return customFieldService.save(customField);
    }

    @PutMapping(value = "/{customFieldId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomField update(@PathVariable Integer customFieldId, @RequestBody CustomField customField) {
        return customFieldService.update(customField);
    }

    @DeleteMapping("/{customFieldId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer customFieldId) {
        customFieldService.delete(customFieldId);
    }
}
