package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.CustomField;

@Transactional(readOnly = true)
public interface CustomFieldRepository extends JpaRepository<CustomField, Integer> {
}
