package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.*;

import java.util.List;

public interface BoardService {
    Board getOne(Integer Id);
    List<Board> findAll();
    Board save(Integer userId, Board board);
    Board update(Board updatedBoard);
    void delete(Integer boardId);
    User getBoardOwner(Integer boardId);
    List<Task> getTasks(Integer boardId);
    List<Task> getTaskById(Integer boardId, Integer taskId);
    List<User> getUsersById(Integer boardId);
    List<BoardStatus> getBoardStatuses(Integer boardId);
    BoardStatus addBoardStatus(Integer boardId, TaskStatus taskStatus);
}
