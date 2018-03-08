package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.repository.BoardRepository;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.repository.UserRepository;
import ru.compito.taskmanager.service.BoardService;

import java.util.List;

@Service(value = "BoardService")
@Transactional
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

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
    public List<Task> getUsers(Integer boardId) {
        return null;
    }

}
