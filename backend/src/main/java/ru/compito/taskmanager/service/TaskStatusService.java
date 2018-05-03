package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.TaskStatus;

import java.util.List;

public interface TaskStatusService {
    TaskStatus getOne(Integer Id);
    List<TaskStatus> findAll();
    TaskStatus save(TaskStatus taskStatus);
    TaskStatus update(TaskStatus updatedTaskStatus);
    void delete(Integer taskStatusId);
}
