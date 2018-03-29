package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.TaskTemplate;

@Transactional(readOnly = true)
public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Integer> {
}
