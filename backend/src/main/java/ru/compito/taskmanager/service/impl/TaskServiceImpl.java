package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.repository.CustomFieldRepository;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.repository.TaskTemplateRepository;
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
    @Autowired
    private TaskTemplateRepository taskTemplateRepository;
    @Autowired
    private CustomFieldRepository customFieldRepository;

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
        return userRepository.getOne(userId).getTasks();
    }

    @Override
    public Task save(Integer userId, Task task) {
        User user = userRepository.getOne(userId);
        task.setAuthor(user);
        task.getUsers().add(user);
        return taskRepository.save(task);
    }

    @Override
    public void delete(Integer taskId) {
        Task task = taskRepository.getOne(taskId);
        customFieldRepository.deleteAllByTask(task);
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
    public Task getTaskByUserId(Integer userId, Integer taskId) {
        User user = userRepository.findOne(userId);
        return taskRepository.findByUsersAndId(user, taskId);
    }

    @Override
    public List<Task> findByTaskTemplateId(Integer taskTemplateId) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        return taskRepository.findAllByTaskTemplate(taskTemplate);
    }

    @Override
    public Task getByTaskTemplateId(Integer taskTemplateId) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        return taskRepository.findByTaskTemplate(taskTemplate);
    }

    @Override
    public Task getByTaskTemplateAndTaskId(Integer taskTemplateId, Integer taskId) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        return taskRepository.findByTaskTemplateAndId(taskTemplate,taskId);
    }

    @Override
    public Task updateTaskTemplate(Integer taskTemplateId, Integer taskId) {
        Task task = taskRepository.getOne(taskId);
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        task.setTaskTemplate(taskTemplate);
        return taskRepository.save(task);
    }

    @Override
    public void addUser(Task task, User user) {
        User newUser = userRepository.getOne(user.getId());
        List<User> users = task.getUsers();
        users.add(newUser);
        task.setUsers(users);
        taskRepository.save(task);
    }

    @Override
    public Task update(Task updatedTask) {
      return taskRepository.save(updatedTask);
    }
}
