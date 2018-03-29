package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Attribute;

import java.util.List;

public interface AttributeService {
    Attribute getOne(Integer Id);
    List<Attribute> findAll();
    Attribute save(Attribute attribute);
    void update(Attribute updatedAttribute);
    void delete(Integer attributeId);
}
