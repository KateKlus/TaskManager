package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public List<Role> getAll() {
        return roleService.findAll();
    }
    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getOne(@PathVariable Integer id) {
        return roleService.getOne(id);
    }
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }
    @PutMapping(value = "/{id}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable Integer id, @RequestBody Role role) {
        roleService.update(role);
    }
    @DeleteMapping("/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        roleService.delete(id);
    }
}
