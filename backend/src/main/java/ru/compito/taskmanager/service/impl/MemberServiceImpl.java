package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Member;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.repository.BoardRepository;
import ru.compito.taskmanager.repository.MemberRepository;
import ru.compito.taskmanager.repository.UserRepository;
import ru.compito.taskmanager.service.MemberService;

import java.util.List;

@Service(value = "MemberService")
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member save(Member member) {
        Member newMember = new Member(member.getUser(),member.getBoard(), member.getRole());
        return memberRepository.save(newMember);
    }

    @Override
    public Member update(Member member) {
        Member updatedMember = new Member(member.getUser(),member.getBoard(), member.getRole());
        return memberRepository.save(updatedMember);
    }

    @Override
    public List<Member> getAllByUserId(Integer userId) {
        User user = userRepository.getOne(userId);
        return memberRepository.findAllByUser(user);
    }

    @Override
    public void deleteMember(Integer userId, Integer boardId) {
        User user = userRepository.getOne(userId);
        Board board = boardRepository.getOne(boardId);
        memberRepository.deleteByUserAndBoard(user,board);
    }

}
