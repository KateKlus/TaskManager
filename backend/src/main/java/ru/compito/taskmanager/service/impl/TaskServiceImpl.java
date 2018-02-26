package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.repository.UserRepository;
import ru.compito.taskmanager.service.TaskService;

import java.util.Collections;
import java.util.List;

@Service(value = "TaskService")
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Task getOne(Integer Id) {
        return taskRepository.getOne(Id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByUserId(Integer userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task save(Integer userId, Task task) {
        User user = userRepository.getOne(userId);
        task.setAuthor(user.getUsername());
        task.getUsers().add(user);
        Task savedTask = taskRepository.save(task);
        savedTask.setUsers(Collections.emptyList());

        return savedTask;
    }

    @Override
    public void delete(Integer taskId) {
        Task task = taskRepository.getOne(taskId);
        task.setUsers(Collections.emptyList());
        taskRepository.save(task);
        taskRepository.delete(task);
    }

    @Override
    public void deleteAllForUser(Integer userId) {
        User user = userRepository.getOne(userId);
        user.setTasks(Collections.emptyList());
        userRepository.save(user);
    }

    @Override
    public void update(Task updatedTask) {
      taskRepository.save(updatedTask);
    }
}
