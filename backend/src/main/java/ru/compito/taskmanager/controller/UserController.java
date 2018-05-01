package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Member;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.pojos.UserRegistration;
import ru.compito.taskmanager.service.BoardService;
import ru.compito.taskmanager.service.MemberService;
import ru.compito.taskmanager.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = ServiceConstants.USER_PATH)
public class UserController{

    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;


    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{userId}/",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }


    @GetMapping(value = "/{userId}/boards/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Board> getBoardsByUserId(@PathVariable Integer userId) {
        return userService.getBoardsByUserId(userId);
    }

    @GetMapping(value = "/{userId}/members/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Member> getMembersByUserId(@PathVariable Integer userId) {
        return memberService.getAllByUserId(userId);
    }

    @GetMapping(value = "/{userId}/boards/{boardId}/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Board getBoardByUserId(@PathVariable Integer userId,@PathVariable Integer boardId ) {
        return userService.getBoardByUserId(userId,boardId);
    }

    @PostMapping(value = "/{userId}/boards/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Board createBoard(@RequestBody Board board, @PathVariable Integer userId) {
        return boardService.save(userId,board);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PutMapping(value = "/{userId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable Integer userId, @RequestBody User user) {
        userService.updateUserById(userId,user);
    }

    @DeleteMapping(value = "/{userId}/boards/{boardId}/")
    public void deleteMember(@PathVariable Integer userId,@PathVariable Integer boardId) {
        memberService.deleteMember(userId,boardId);
    }

    @DeleteMapping("/{userId}/")
    public void delete(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
    }

}
