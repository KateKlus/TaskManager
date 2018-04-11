package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Role;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.BoardService;
import ru.compito.taskmanager.service.RoleService;
import ru.compito.taskmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getAll() {
        return roleService.findAll();
    }

    @GetMapping(value = "/{roleId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getOne(@PathVariable Integer roleId) {
        return roleService.getOne(roleId);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role addRole(@RequestBody Role role) {
        User currentUser = userService.findByUsername(SecurityContextHolder
                .getContext().getAuthentication().getName());
        User boardOwner = boardService.getBoardOwner(role.getBoard().getId());
        Role newRole = null;
        if(currentUser.equals(boardOwner)){
            newRole = roleService.save(role);
        }
        return newRole;
    }

    @PutMapping(value = "/{roleId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role update(@PathVariable Integer roleId, @RequestBody Role role) {
        return roleService.update(role);
    }

    @DeleteMapping("/{roleId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer roleId) {
        roleService.delete(roleId);
    }

}
