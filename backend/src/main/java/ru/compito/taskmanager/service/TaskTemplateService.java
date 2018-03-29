package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.TaskTemplate;

import java.util.List;

public interface TaskTemplateService {
    TaskTemplate getOne(Integer Id);
    List<TaskTemplate> findAll();
    TaskTemplate save(TaskTemplate taskTemplate);
    void update(TaskTemplate updatedTaskTemplate);
    void delete(Integer taskTemplateId);
}
