package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public List<Member> getAll() {
        return memberService.findAll();
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Member addRole(@RequestBody Member member) {
        User currentUser = userService.findByUsername(SecurityContextHolder
                .getContext().getAuthentication().getName());
        User boardOwner = boardService.getBoardOwner(member.getBoard().getId());
        Member newMember = null;
        if(currentUser.equals(boardOwner)){
            newMember = memberService.save(member);
        }
        return newMember;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Member update(@RequestBody Member member) {
        return memberService.update(member);
    }

}
