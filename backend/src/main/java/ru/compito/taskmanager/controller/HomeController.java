package ru.compito.taskmanager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping(value="/")
    public String index(){
        return "index";
    }

    @GetMapping(value="/registration")
    public String register(){
        return "register";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }
}
