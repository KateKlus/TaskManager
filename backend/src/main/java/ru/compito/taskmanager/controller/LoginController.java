package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.pojos.UserRegistration;
import ru.compito.taskmanager.service.UserService;

import java.util.regex.Pattern;

@RestController
@RequestMapping(value = ServiceConstants.API_URL)
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenStore tokenStore;

    @PostMapping(value = "register/")
    public @ResponseBody ResponseEntity<Object> register(@RequestBody UserRegistration userRegistration){
        if(!userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
            return new ResponseEntity<>("Error the two passwords do not match", HttpStatus.NOT_ACCEPTABLE);
        else if(userService.findByUsername(userRegistration.getUsername()) != null)
            return new ResponseEntity<>("Error this username already exists", HttpStatus.NOT_ACCEPTABLE);

        //Проверка на наличии специальных символов в имени.
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");
        if(!pattern.matcher(userRegistration.getUsername()).find())
            return new ResponseEntity<>("No special characters are allowed in the username", HttpStatus.NOT_ACCEPTABLE);
        userService.saveUser(new User(userRegistration.getUsername(),userRegistration.getFullname(),
                userRegistration.getPassword(),userRegistration.getEmail()));

        return new ResponseEntity<>("User created", HttpStatus.OK);
    }

    @GetMapping(value = "logouts/")
    public void logout(@RequestParam (value = "access_token") String accessToken){
        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));
    }

    @GetMapping(value ="getUserId/")
    public @ResponseBody ResponseEntity<Integer> getUserId(){
        User user = userService.findByUsername(SecurityContextHolder
                .getContext().getAuthentication().getName());
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

}
