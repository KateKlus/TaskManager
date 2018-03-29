package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.repository.AttributeRepository;
import ru.compito.taskmanager.service.AttributeService;

import java.util.List;

@Service(value = "AttributeService")
@Transactional
public class AttributeServiceImpl implements AttributeService{

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public Attribute getOne(Integer Id) {
        return attributeRepository.getOne(Id);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Override
    public void update(Attribute updatedAttribute) {
        attributeRepository.save(updatedAttribute);
    }

    @Override
    public void delete(Integer attributeId) {
        attributeRepository.delete(attributeId);
    }
}
