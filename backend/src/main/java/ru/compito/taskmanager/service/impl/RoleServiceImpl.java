package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Role;
import ru.compito.taskmanager.repository.RoleRepository;
import ru.compito.taskmanager.service.RoleService;

import java.util.List;

@Service(value = "RoleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getOne(Integer roleId) {
        return roleRepository.getOne(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role updatedRole) {
        return roleRepository.save(updatedRole);
    }

    @Override
    public void delete(Integer roleId) {
        roleRepository.delete(roleId);
    }
}
