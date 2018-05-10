package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.entity.User;

import java.util.List;

public interface TaskService {
    Task getOne(Integer Id);
    List<Task> findAll();
    List<Task> findByUserId(Integer userId);
    Task save(Integer userId, Task task);
    Task update(Task updatedTask);
    void delete(Integer taskId);
    void deleteAllForUser(Integer userId);
    Task getTaskByUserId(Integer userId, Integer taskId);
    List<Task> findByTaskTemplateId(Integer taskTemplateId);
    Task getByTaskTemplateId(Integer taskTemplateId);
    Task getByTaskTemplateAndTaskId(Integer taskTemplateId, Integer taskId);
    Task updateTaskTemplate(Integer taskTemplateId, Integer taskId);
    void addUser(Task task, User user);
}
