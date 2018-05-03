package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<CustomField> customFields = customFieldService.findAll();
        return new ResponseEntity<>(customFields, HttpStatus.OK);
    }

    @GetMapping(value = "/{customFieldId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CustomField> getOne(@PathVariable Integer customFieldId) {
        CustomField customField = customFieldService.getOne(customFieldId);
        return new ResponseEntity<>(customField, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<CustomField> createCustomField(@RequestBody CustomField customField) {
        CustomField newCustomField = customFieldService.save(customField);
        return new ResponseEntity<>(newCustomField, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{customFieldId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CustomField> update(@PathVariable Integer customFieldId, @RequestBody CustomField customField) {
        CustomField updatedCustomField = customFieldService.update(customField);
        return new ResponseEntity<>(updatedCustomField, HttpStatus.OK);
    }

    @DeleteMapping("/{customFieldId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer customFieldId) {
        customFieldService.delete(customFieldId);
    }
}
