package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Integer Id);
    User findByUsername(String username);
    User saveUser(User user);
    void deleteUserById(Integer Id);
    User updateUserById(Integer userId, User user);
    List<User> findByTaskId(Integer taskId);
    List<Board> getBoardsByUserId(Integer userId);
    Board getBoardByUserId(Integer userId, Integer boardId);
}
