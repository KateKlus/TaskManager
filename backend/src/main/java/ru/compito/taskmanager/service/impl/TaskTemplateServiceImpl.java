package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.repository.TaskTemplateRepository;
import ru.compito.taskmanager.service.TaskTemplateService;

import java.util.List;

@Service(value = "TaskTemplateService")
@Transactional
public class TaskTemplateServiceImpl implements TaskTemplateService{

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Override
    public TaskTemplate getOne(Integer Id) {
        return taskTemplateRepository.getOne(Id);
    }

    @Override
    public List<TaskTemplate> findAll() {
        return taskTemplateRepository.findAll();
    }

    @Override
    public TaskTemplate save(TaskTemplate taskTemplate) {
        return taskTemplateRepository.save(taskTemplate);
    }

    @Override
    public TaskTemplate update(TaskTemplate updatedTaskTemplate) {
        return taskTemplateRepository.save(updatedTaskTemplate);
    }

    @Override
    public void delete(Integer taskTemplateId) {
        taskTemplateRepository.delete(taskTemplateId);
    }

}
