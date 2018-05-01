package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Attribute;
import ru.compito.taskmanager.entity.TaskTemplate;

import java.util.List;

@Transactional(readOnly = true)
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    Attribute findByTaskTemplatesAndId(TaskTemplate taskTemplate, Integer attributeId);
    List<Attribute> findAllByTaskTemplates(TaskTemplate taskTemplate);
    void deleteAllByTaskTemplates(TaskTemplate taskTemplate);
}
