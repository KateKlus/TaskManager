package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.*;
import ru.compito.taskmanager.repository.*;
import ru.compito.taskmanager.service.BoardService;

import java.util.*;

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
    @Autowired
    private RoleRepository roleRepository;

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
        Role role = new Role("Owner",user,board);
        roleRepository.save(role);
        return boardRepository.save(board);
    }

    @Override
    public Board update(Board updatedBoard) {
        return boardRepository.save(updatedBoard);
    }

    @Override
    public void delete(Integer boardId) {
        Board board = boardRepository.getOne(boardId);
        boardStatusRepository.deleteAllByBoard(board);
        List<Task> tasks = taskRepository.findAllByBoard(board);
        for(Task task:tasks){
            task.setUsers(Collections.emptyList());
            taskRepository.save(task);
        }
        taskRepository.deleteAllByBoard(board);
        boardRepository.delete(board);
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
        List<Role> roles = roleRepository.findAllByBoard(board);
        List<User> userList = new ArrayList<>();
        for(Role role : roles)
            userList.add(role.getUser());
        return userList;
    }
    @Override
    public void addBoardStatus(Integer boardId, TaskStatus taskStatus) {
        Board board = boardRepository.getOne(boardId);
        BoardStatus boardStatus = new BoardStatus(board, taskStatus);
        boardStatusRepository.save(boardStatus);
    }

    @Override
    public List<BoardStatus> getBoardStatuses(Integer boardId) {
        Board board = boardRepository.getOne(boardId);
        List<BoardStatus> boardStatuses = boardStatusRepository.findAllByBoard(board);
        return boardStatuses;
    }

}
