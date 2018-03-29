package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.CustomField;

import java.util.List;

public interface CustomFieldService {
    CustomField getOne(Integer Id);
    List<CustomField> findAll();
    CustomField save(CustomField customField);
    void update(CustomField updatedCustomField);
    void delete(Integer customFieldId);
}
