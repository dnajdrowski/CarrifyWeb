package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.model.Role.Role;
import com.carrify.web.carrifyweb.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
