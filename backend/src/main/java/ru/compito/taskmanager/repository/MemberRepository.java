package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.*;
import ru.compito.taskmanager.entity.enums.RoleType;

import java.util.List;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member,MemberIdentity>{
    List<Member> findAllByBoard(Board board);
    List<Member> findAllByUser(User user);
    Member findByUserAndBoard(User user, Board board);
    void deleteAllByRole(RoleType role);
    void deleteAllByBoard(Board board);
    void deleteByUserAndBoard(User user, Board board);
    void  deleteAllByUser(User user);
}
