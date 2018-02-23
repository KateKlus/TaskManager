package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.compito.taskmanager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
}
