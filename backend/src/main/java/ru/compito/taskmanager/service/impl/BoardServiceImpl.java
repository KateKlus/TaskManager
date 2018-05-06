package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.*;
import ru.compito.taskmanager.entity.enums.RoleType;
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
    private MemberRepository memberRepository;


    @Override
    public Board getOne(Integer Id) {
        return boardRepository.findOne(Id);
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board save(Integer userId, Board board) {
        User user = userRepository.getOne(userId);
        board.setBoardOwner(user);
        /**TODO
        if(!roleRepository.existsByRoleName("Owner"))
            roleRepository.save(new Role("Owner"));
        Role role = roleRepository.findByRoleName("Owner");
         */
        Member member = new Member(user,board, RoleType.OWNER);
        Board newBoard = boardRepository.save(board);
        memberRepository.save(member);
        return newBoard;
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
        memberRepository.deleteAllByBoard(board);
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
        List<Member> members = memberRepository.findAllByBoard(board);
        List<User> userList = new ArrayList<>();
        for(Member member : members)
            userList.add(member.getUser());
        return userList;
    }
    @Override
    public BoardStatus addBoardStatus(Integer boardId, TaskStatus taskStatus) {
        Board board = boardRepository.getOne(boardId);
        BoardStatus boardStatus = new BoardStatus(board, taskStatus);
        return boardStatusRepository.save(boardStatus);
    }

    @Override
    public List<BoardStatus> getBoardStatuses(Integer boardId) {
        Board board = boardRepository.getOne(boardId);
        List<BoardStatus> boardStatuses = boardStatusRepository.findAllByBoard(board);
        return boardStatuses;
    }

}
