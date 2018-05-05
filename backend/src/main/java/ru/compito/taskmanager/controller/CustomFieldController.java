package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.service.ContentRelatedRoleService;
import ru.compito.taskmanager.service.CustomFieldService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.CUSTOMFIELD_PATH)
public class CustomFieldController {

    @Autowired
    private CustomFieldService customFieldService;
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<CustomField> customFields = customFieldService.findAll();
        return new ResponseEntity<>(customFields, HttpStatus.OK);
    }

    @GetMapping(value = "/{customFieldId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CustomField> getOne(@PathVariable Integer customFieldId) {
        CustomField customField = customFieldService.getOne(customFieldId);
        if(customField==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer boardId = customField.getTask().getBoard().getId();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)||
                contentRelatedRoleService.isContentModerator(boardId,authentication)) {
            CustomField updatedCustomField = customFieldService.update(customField);
            return new ResponseEntity<>(updatedCustomField, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }

    }

    @DeleteMapping("/{customFieldId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer customFieldId) {
        CustomField customField = customFieldService.getOne(customFieldId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer boardId = customField.getTask().getBoard().getId();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication) ||
                contentRelatedRoleService.isContentModerator(boardId,authentication)) {
            customFieldService.delete(customFieldId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
    }
}
