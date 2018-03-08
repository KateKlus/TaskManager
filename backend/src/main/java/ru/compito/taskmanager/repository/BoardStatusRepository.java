package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.BoardStatus;
import ru.compito.taskmanager.entity.BoardStatusIdentity;

@Transactional(readOnly = true)
public interface BoardStatusRepository extends JpaRepository<BoardStatus, BoardStatusIdentity> {
}
