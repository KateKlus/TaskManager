package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Member;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.BoardService;
import ru.compito.taskmanager.service.MemberService;
import ru.compito.taskmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.MEMBER_PATH)
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Member> addRole(@RequestBody Member member) {
        User currentUser = userService.findByUsername(SecurityContextHolder
                .getContext().getAuthentication().getName());
        User boardOwner = boardService.getBoardOwner(member.getBoard().getId());
        Member newMember = null;
        if(currentUser.equals(boardOwner)){
            newMember = memberService.save(member);
        }
        return new ResponseEntity<>(newMember, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Member> update(@RequestBody Member member) {
        Member updatedMember = memberService.update(member);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

}
