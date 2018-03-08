package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;

import java.util.List;

public interface BoardService {
    Board getOne(Integer Id);
    List<Board> findAll();
    Board save(Integer userId, Board board);
    void update(Board updatedBoard);
    void delete(Integer boardId);
    User getBoardOwner(Integer boardId);
    List<Task> getTasks(Integer boardId);
    List<Task> getTaskById(Integer boardId, Integer taskId);
    List<Task> getUsers(Integer boardId);
}
