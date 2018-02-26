package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{Id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Integer Id) {
        return userService.getUserById(Id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PutMapping(value = "/{Id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable Integer Id, @Valid @RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{Id}")
    public void delete(@PathVariable Integer Id) {
        userService.deleteUserById(Id);
    }

}
