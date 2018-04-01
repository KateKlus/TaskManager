package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Attribute;

import java.util.List;

public interface AttributeService {
    Attribute getOne(Integer Id);
    List<Attribute> findAll();
    Attribute save(Integer taskTemplateId, Attribute attribute);
    Attribute update(Integer attributeId, Attribute updatedAttribute);
    void delete(Integer attributeId);
    List<Attribute> findByTaskTemplateId(Integer taskTemplateId);
    Attribute getByTaskTemplateAndAttributeId(Integer taskTemplateId, Integer attributeId);
    void deleteAllForTaskTemplate(Integer taskTemplateId);
}
