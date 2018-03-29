package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.CustomField;

import java.util.List;

@Transactional(readOnly = true)
public interface CustomFieldRepository extends JpaRepository<CustomField, Integer> {
    List<CustomField> findAllByAttribute(Attribute attribute);
    CustomField findByAttributeAndId(Attribute attribute, Integer customFieldId);
}
