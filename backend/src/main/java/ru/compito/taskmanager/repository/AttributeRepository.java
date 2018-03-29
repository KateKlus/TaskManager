package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Attribute;

@Transactional(readOnly = true)
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
}
