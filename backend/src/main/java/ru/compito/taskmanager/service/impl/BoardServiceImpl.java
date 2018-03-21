package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.*;
import ru.compito.taskmanager.repository.BoardRepository;
import ru.compito.taskmanager.repository.BoardStatusRepository;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.repository.UserRepository;
import ru.compito.taskmanager.service.BoardService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "BoardService")
@Transactional
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BoardStatusRepository boardStatusRepository;

    @Override
    public Board getOne(Integer Id) {
        return boardRepository.getOne(Id);
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board save(Integer userId, Board board) {
        User user = userRepository.getOne(userId);
        board.setBoardOwner(user);
        return boardRepository.save(board);
    }

    @Override
    public void update(Board updatedBoard) {
        boardRepository.save(updatedBoard);
    }

    @Override
    public void delete(Integer boardId) {
        boardRepository.delete(boardId);
    }

    @Override
    public User getBoardOwner(Integer boardId) {
        return boardRepository.getOne(boardId).getBoardOwner();
    }

    @Override
    public List<Task> getTasks(Integer boardId) {
        Board board = boardRepository.getOne(boardId);
        return taskRepository.findAllByBoard(board);
    }

    @Override
    public List<Task> getTaskById(Integer boardId, Integer taskId) {
        Board board = boardRepository.getOne(boardId);
         return taskRepository.findByBoardAndId(board,taskId);
    }

    @Override
    public List<User> getUsersById(Integer boardId) {
        Board board = boardRepository.getOne(boardId);
        List<Task> tasks = taskRepository.findAllByBoard(board);
        Set<User> users = new HashSet<>();
        List<User> userList = new ArrayList<>();
        for(Task task : tasks){
            users.addAll(userRepository.findAllByTasks(task));
        }
        userList.addAll(users);
        return userList;
    }

    @Override
    public List<BoardStatus> getBoardStatuses(Integer boardId) {
        Board board = boardRepository.getOne(boardId);
        List<BoardStatus> boardStatuses = boardStatusRepository.findAllByBoard(board);
        /*Set<TaskStatus> taskStatuses = new HashSet<>();
        for(BoardStatus boardStatus : boardStatuses)
            taskStatuses.add(boardStatus.getTaskStatus());
        List<TaskStatus> taskStatusList = new ArrayList<>();
        taskStatusList.addAll(taskStatuses);
        return taskStatusList;*/
        return boardStatuses;
    }

}
