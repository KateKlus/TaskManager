package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;

import java.util.List;

@Transactional(readOnly = true)
public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAllByBoardOwner(User boardOwner);
    Board findByBoardOwnerAndId(User boardOwner, Integer boardId);
}
