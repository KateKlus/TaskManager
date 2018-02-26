package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Integer Id);
    void saveUser(User user);
    void deleteUserById(Integer Id);
    void updateUser(User user);
    List<User> findByTaskId(Integer taskId);

}
