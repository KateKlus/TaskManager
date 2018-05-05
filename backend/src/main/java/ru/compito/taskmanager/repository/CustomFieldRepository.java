package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.CustomField;
import ru.compito.taskmanager.entity.Task;

import java.util.List;

@Transactional(readOnly = true)
public interface CustomFieldRepository extends JpaRepository<CustomField, Integer> {
    List<CustomField> findAllByAttribute(Attribute attribute);
    CustomField findFirstByAttribute(Attribute attribute);
    CustomField findByAttributeAndId(Attribute attribute, Integer customFieldId);
    List<CustomField> findAllByTask(Task task);
    void deleteAllByTask(Task task);
    void deleteAllByAttribute(Attribute attribute);
}
