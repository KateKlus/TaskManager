package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Member;

import java.util.List;

public interface MemberService {
    List<Member> findAll();
    Member save(Member member);
    Member update(Member updatedMember);
    List<Member> getAllByUserId(Integer userId);
    void deleteMember(Integer userId, Integer boardId);
}
