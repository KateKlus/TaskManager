package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.CustomField;

import java.util.List;

public interface CustomFieldService {
    CustomField getOne(Integer Id);
    List<CustomField> findAll();
    CustomField save(CustomField customField);
    CustomField update(CustomField updatedCustomField);
    void delete(Integer customFieldId);
    List<CustomField> findByAttributeId(Integer attributeId);
    CustomField getByAttributeAndCustomFieldId(Integer attributeId, Integer customFieldId);
    CustomField getByAttribute(Integer attributeId);
    List<CustomField> getAllCustomFieldsByTask(Integer taskId);
}
