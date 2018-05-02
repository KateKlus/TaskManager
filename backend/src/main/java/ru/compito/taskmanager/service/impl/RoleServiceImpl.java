package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.Role;
import ru.compito.taskmanager.repository.RoleRepository;
import ru.compito.taskmanager.repository.MemberRepository;
import ru.compito.taskmanager.service.RoleService;

import java.util.List;

@Service(value = "RoleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Role getOne(Integer Id) {
        return roleRepository.getOne(Id);
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
    public void update(Role updatedRole) {
        roleRepository.save(updatedRole);
    }

    @Override
    public void delete(Integer roleId) {
        Role role = roleRepository.getOne(roleId);
        memberRepository.deleteAllByRole(role);
        roleRepository.delete(roleId);
    }
}
