package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.pojos.UserRegistration;
import ru.compito.taskmanager.service.BoardService;
import ru.compito.taskmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping(value = "/register/")
    public String register(@RequestBody UserRegistration userRegistration){
        if(!userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
            return "Error the two passwords do not match";
        else if(userService.findByUsername(userRegistration.getUsername()) != null)
            return "Error this username already exists";

        //Проверка на наличии специальных символов в имени.
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");
        if(!pattern.matcher(userRegistration.getUsername()).find())
            return "No special characters are allowed in the username";

        userService.saveUser(new User(userRegistration.getUsername(),
                userRegistration.getPassword(),userRegistration.getEmail()));

        return "User created";
    }

    @GetMapping(value = "/logouts/")
    public void logout(@RequestParam (value = "access_token") String accessToken){
        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));
    }

    @GetMapping(value ="/getUserId/")
    public Integer getUserId(){
        User user = userService.findByUsername(SecurityContextHolder
                .getContext().getAuthentication().getName());
        return user.getId();
    }

    @GetMapping(value = "/users/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/users/{Id}/",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Integer Id) {
        return userService.getUserById(Id);
    }


    @GetMapping(value = "/users/{Id}/boards/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Board> getBoardsByUserId(@PathVariable Integer Id) {
        return userService.getBoardsByUserId(Id);
    }

    @GetMapping(value = "/users/{Id}/boards/{boardId}/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Board getBoardByUserId(@PathVariable Integer Id,@PathVariable Integer boardId ) {
        return userService.getBoardByUserId(Id,boardId);
    }

    @PostMapping(value = "/users/{Id}/boards/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Board createBoard(@RequestBody Board board, @PathVariable Integer Id) {
        return boardService.save(Id,board);
    }

    @PostMapping(value = "/users/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PutMapping(value = "/users/{Id}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable Integer Id, @RequestBody User user) {
        userService.updateUserById(Id,user);
    }

    @DeleteMapping("/users/{Id}/")
    public void delete(@PathVariable Integer Id) {
        userService.deleteUserById(Id);
    }

}
