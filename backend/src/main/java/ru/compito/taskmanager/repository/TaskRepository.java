package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;

import java.util.List;

@Transactional(readOnly = true)
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByBoard(Board board);
    List<Task> findByBoardAndId(Board board,Integer taskId);
    Task findByUsersAndId(User user, Integer taskId);
    @Query("SELECT u.createdTasks FROM User u WHERE u.username=:username")
    List<Task> findByUsername(String username);

}