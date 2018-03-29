package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.repository.CustomFieldRepository;
import ru.compito.taskmanager.service.CustomFieldService;

import java.util.List;

@Service(value = "CustomFieldService")
@Transactional
public class CustomFieldServiceImpl implements CustomFieldService{

    @Autowired
    private CustomFieldRepository customFieldRepository;

    @Override
    public CustomField getOne(Integer Id) {
        return customFieldRepository.getOne(Id);
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
    public void update(CustomField updatedCustomField) {
        customFieldRepository.save(updatedCustomField);
    }

    @Override
    public void delete(Integer customFieldId) {
        customFieldRepository.delete(customFieldId);
    }
}
