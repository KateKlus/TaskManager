package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.repository.AttributeRepository;
import ru.compito.taskmanager.repository.CustomFieldRepository;
import ru.compito.taskmanager.repository.TaskRepository;
import ru.compito.taskmanager.service.CustomFieldService;

import java.util.List;

@Service(value = "CustomFieldService")
@Transactional
public class CustomFieldServiceImpl implements CustomFieldService{

    @Autowired
    private CustomFieldRepository customFieldRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public CustomField getOne(Integer Id) {
        return customFieldRepository.findOne(Id);
    }

    @Override
    public List<CustomField> findAll() {
        return customFieldRepository.findAll();
    }

    @Override
    public CustomField save(CustomField customField) {
        return customFieldRepository.save(customField);
    }

    @Override
    public CustomField update(CustomField updatedCustomField) {
        return customFieldRepository.save(updatedCustomField);
    }

    @Override
    public void delete(Integer customFieldId) {
        customFieldRepository.delete(customFieldId);
    }

    @Override
    public List<CustomField> findByAttributeId(Integer attributeId) {
        Attribute attribute = attributeRepository.getOne(attributeId);
        return customFieldRepository.findAllByAttribute(attribute);
    }

    @Override
    public CustomField getByAttributeAndCustomFieldId(Integer attributeId, Integer customFieldId) {
        Attribute attribute = attributeRepository.getOne(attributeId);
        return customFieldRepository.findByAttributeAndId(attribute,customFieldId);
    }

    @Override
    public CustomField getByAttribute(Integer attributeId) {
        Attribute attribute = attributeRepository.getOne(attributeId);
        return customFieldRepository.findFirstByAttribute(attribute);
    }

    @Override
    public List<CustomField> getAllCustomFieldsByTask(Integer taskId) {
        Task task = taskRepository.getOne(taskId);
        return customFieldRepository.findAllByTask(task);
    }

}
