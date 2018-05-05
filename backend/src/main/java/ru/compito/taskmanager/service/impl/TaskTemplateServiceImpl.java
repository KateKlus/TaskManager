package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.repository.AttributeRepository;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.repository.TaskTemplateRepository;
import ru.compito.taskmanager.service.TaskTemplateService;

import java.util.Collections;
import java.util.List;

@Service(value = "TaskTemplateService")
@Transactional
public class TaskTemplateServiceImpl implements TaskTemplateService{

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskTemplate getOne(Integer Id) {
        return taskTemplateRepository.findOne(Id);
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
    public TaskTemplate update(Integer taskTemplateId, TaskTemplate updatedTaskTemplate) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        updatedTaskTemplate.setAttributes(taskTemplate.getAttributes());
        return taskTemplateRepository.save(updatedTaskTemplate);
    }

    @Override
    public void delete(Integer taskTemplateId) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        List<Task> tasks = taskRepository.findAllByTaskTemplate(taskTemplate);
        for(Task task : tasks){
            task.setTaskTemplate(null);
            taskRepository.save(task);
        }
        taskTemplate.setAttributes(Collections.emptyList());
        taskTemplateRepository.save(taskTemplate);
        attributeRepository.deleteAllByTaskTemplates(taskTemplate);
        taskTemplateRepository.delete(taskTemplate);
    }

}
