package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Role;
import ru.compito.taskmanager.service.RoleService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.ROLE_PATH)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Role> getOne(@PathVariable Integer id) {
        Role role = roleService.getOne(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role newRole = roleService.save(role);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Role> update(@PathVariable Integer id, @RequestBody Role role) {
        Role updatedRole = roleService.update(role);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        roleService.delete(id);
    }
}
