package ru.compito.taskmanager.service;

import ru.compito.taskmanager.entity.Role;

import java.util.List;

public interface RoleService {
    Role getOne(Integer Id);
    List<Role> findAll();
    Role save(Role role);
    void update(Role updatedRole);
    void delete(Integer roleId);
}
