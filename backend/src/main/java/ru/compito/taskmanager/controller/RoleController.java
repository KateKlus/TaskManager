package ru.compito.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.enums.RoleType;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.ROLE_PATH)
public class RoleController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<RoleType> roleTypes = Arrays.asList(RoleType.values());
        return new ResponseEntity<>(roleTypes, HttpStatus.OK);
    }

}
