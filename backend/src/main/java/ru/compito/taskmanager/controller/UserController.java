package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Member;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.pojos.UserRegistration;
import ru.compito.taskmanager.service.BoardService;
import ru.compito.taskmanager.service.ContentRelatedRoleService;
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
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;


    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if(user==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping(value = "/{userId}/boards/",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getBoardsByUserId(@PathVariable Integer userId) {
        List<Board> boards = userService.getBoardsByUserId(userId);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/members/",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getMembersByUserId(@PathVariable Integer userId) {
        List<Member> members = memberService.getAllByUserId(userId);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/boards/{boardId}/",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Board> getBoardByUserId(@PathVariable Integer userId,@PathVariable Integer boardId ) {
        Board board = userService.getBoardByUserId(userId,boardId);
        if(board==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/boards/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Board> createBoard(@RequestBody Board board, @PathVariable Integer userId) {
        Board newBoard = boardService.save(userId,board);
        return new ResponseEntity<>(newBoard, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<User> update(@PathVariable Integer userId, @RequestBody User user) {
        User updatedUser = userService.updateUserById(userId,user);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{userId}/boards/{boardId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody ResponseEntity<?> deleteMember(@PathVariable Integer userId,@PathVariable Integer boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)) {
            memberService.deleteMember(userId, boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
    }

    @DeleteMapping("/{userId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
    }

}
