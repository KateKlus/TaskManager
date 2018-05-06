package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Member;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.repository.BoardRepository;
import ru.compito.taskmanager.repository.MemberRepository;
import ru.compito.taskmanager.repository.UserRepository;
import ru.compito.taskmanager.service.ContentRelatedRoleService;

import java.security.Principal;

@Service(value = "ContentRelatedRoleService")
@Transactional
public class ContentRelatedRoleServiceImpl implements ContentRelatedRoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;


    @Override
    public boolean isContentOwner(Integer boardId, Authentication authentication) {
        Board board = boardRepository.getOne(boardId);
        User user = userRepository.findByUsername(authentication.getName());
        Member member = memberRepository.findByUserAndBoard(user,board);
        if(member == null)
            return false;
        else
            return member.getRole().toString().toUpperCase().equals("OWNER");
    }

    @Override
    public boolean isContentAdministrator(Integer boardId, Authentication authentication) {
        Board board = boardRepository.getOne(boardId);
        User user = userRepository.findByUsername(authentication.getName());
        Member member = memberRepository.findByUserAndBoard(user,board);
        if(member == null)
            return false;
        else
            return member.getRole().toString().toUpperCase().equals("ADMIN");
    }

    @Override
    public boolean isContentModerator(Integer boardId, Authentication authentication) {
        Board board = boardRepository.getOne(boardId);
        User user = userRepository.findByUsername(authentication.getName());
        Member member = memberRepository.findByUserAndBoard(user,board);
        if(member == null)
            return false;
        else
            return member.getRole().toString().toUpperCase().equals("MODERATOR");
    }

    @Override
    public boolean isContentDeveloper(Integer boardId, Authentication authentication) {
        Board board = boardRepository.getOne(boardId);
        User user = userRepository.findByUsername(authentication.getName());
        Member member = memberRepository.findByUserAndBoard(user,board);
        if(member == null)
            return false;
        else
            return member.getRole().toString().toUpperCase().equals("DEVELOPER");
    }

    @Override
    public boolean isContentViewer(Integer boardId, Authentication authentication) {
        return false;
    }
}
