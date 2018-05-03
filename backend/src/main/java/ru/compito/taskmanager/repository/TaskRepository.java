package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.*;

import java.util.List;

@Transactional(readOnly = true)
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByBoard(Board board);
    List<Task> findByBoardAndId(Board board,Integer taskId);
    Task findByUsersAndId(User user, Integer taskId);
    List<Task> findAllByTaskTemplate(TaskTemplate taskTemplate);
    List<Task> findAllByCurrentStatus(TaskStatus taskStatus);
    Task findByTaskTemplateAndId(TaskTemplate taskTemplate, Integer taskId);
    Task findByTaskTemplate(TaskTemplate taskTemplate);
    void deleteAllByBoard(Board board);

}