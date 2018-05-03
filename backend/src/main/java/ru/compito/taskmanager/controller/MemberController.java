package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.Member;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.BoardService;
import ru.compito.taskmanager.service.ContentRelatedRoleService;
import ru.compito.taskmanager.service.MemberService;
import ru.compito.taskmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.MEMBER_PATH)
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Member> addRole(@RequestBody Member member) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer boardId = member.getBoard().getId();
        Member newMember = null;
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)){
            newMember = memberService.save(member);
            return new ResponseEntity<>(newMember, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Member> update(@RequestBody Member member) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer boardId = member.getBoard().getId();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)) {
            Member updatedMember = memberService.update(member);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
    }

}
