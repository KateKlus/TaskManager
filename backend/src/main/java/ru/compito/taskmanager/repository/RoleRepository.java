package ru.compito.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Role;

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role,Integer>{
    Role findByRoleName(String roleName);
    Boolean existsByRoleName(String roleName);
}
