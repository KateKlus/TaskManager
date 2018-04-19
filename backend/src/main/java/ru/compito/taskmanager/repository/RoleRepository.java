package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Role;
import ru.compito.taskmanager.entity.User;

import java.util.List;

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role,Integer>{
    List<Role> findAllByBoard(Board board);
    List<Role> findAllByUser(User user);
}
