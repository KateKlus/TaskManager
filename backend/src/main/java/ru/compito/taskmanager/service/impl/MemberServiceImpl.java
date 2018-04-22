package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Member;
import ru.compito.taskmanager.repository.MemberRepository;
import ru.compito.taskmanager.service.MemberService;

import java.util.List;

@Service(value = "MemberService")
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member update(Member updatedMember) {
        return memberRepository.save(updatedMember);
    }

}
