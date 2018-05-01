package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.BoardStatus;
import ru.compito.taskmanager.entity.BoardStatusIdentity;
import ru.compito.taskmanager.entity.TaskStatus;

import java.util.List;

@Transactional(readOnly = true)
public interface BoardStatusRepository extends JpaRepository<BoardStatus, BoardStatusIdentity> {

List<BoardStatus> findAllByBoard(Board board);
void deleteAllByTaskStatus(TaskStatus taskStatus);
void deleteAllByBoard(Board board);

}
