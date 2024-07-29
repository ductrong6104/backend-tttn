package org.example.dienluc.service.serviceImpl;

import org.example.dienluc.entity.Role;
import org.example.dienluc.repository.RoleRepository;
import org.example.dienluc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return roleRepository.findById(roleId).get();
    }
}
