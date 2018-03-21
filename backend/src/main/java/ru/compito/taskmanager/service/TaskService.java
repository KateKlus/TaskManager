package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Task;

import java.util.List;

public interface TaskService {
    Task getOne(Integer Id);
    List<Task> findAll();
    List<Task> findByUserId(Integer userId);
    List<Task> findByUsername(String username);
    Task save(Integer userId, Task task);
    void update(Task updatedTask);
    void delete(Integer taskId);
    void deleteAllForUser(Integer userId);
    Task getTaskByUserId(Integer userId, Integer taskId);
}
