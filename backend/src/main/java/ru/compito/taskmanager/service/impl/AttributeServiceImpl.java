package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.TaskTemplate;
import ru.compito.taskmanager.repository.AttributeRepository;
import ru.compito.taskmanager.repository.CustomFieldRepository;
import ru.compito.taskmanager.repository.TaskTemplateRepository;
import ru.compito.taskmanager.service.AttributeService;

import java.util.Collections;
import java.util.List;

@Service(value = "AttributeService")
@Transactional
public class AttributeServiceImpl implements AttributeService{

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Autowired
    private CustomFieldRepository customFieldRepository;

    @Override
    public Attribute getOne(Integer Id) {
        return attributeRepository.findOne(Id);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    public Attribute save(Integer taskTemplateId, Attribute attribute) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        attribute.getTaskTemplates().add(taskTemplate);
        return attributeRepository.save(attribute);
    }

    @Override
    public Attribute update(Integer attributeId, Attribute updatedAttribute) {
        Attribute attribute = attributeRepository.getOne(attributeId);
        updatedAttribute.setTaskTemplates(attribute.getTaskTemplates());
        return attributeRepository.save(updatedAttribute);
    }

    @Override
    public void delete(Integer attributeId) {
        Attribute attribute = attributeRepository.getOne(attributeId);
        customFieldRepository.deleteAllByAttribute(attribute);
        attribute.setTaskTemplates(Collections.emptyList());
        attributeRepository.save(attribute);
        attributeRepository.delete(attributeId);
    }

    @Override
    public List<Attribute> findByTaskTemplateId(Integer taskTemplateId) {
        return taskTemplateRepository.getOne(taskTemplateId).getAttributes();
    }

    @Override
    public Attribute getByTaskTemplateAndAttributeId(Integer taskTemplateId, Integer attributeId) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        return attributeRepository.findByTaskTemplatesAndId(taskTemplate,attributeId);
    }

    @Override
    public void deleteAllForTaskTemplate(Integer taskTemplateId) {
        TaskTemplate taskTemplate = taskTemplateRepository.getOne(taskTemplateId);
        taskTemplate.setAttributes(Collections.emptyList());
        taskTemplateRepository.save(taskTemplate);
    }
}
