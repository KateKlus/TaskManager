package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.TaskStatus;

@Transactional(readOnly = true)
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
}
