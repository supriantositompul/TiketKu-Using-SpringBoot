package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.entity.Role;
import com.kelempok7.serverapp.repository.RoleRepository;
import com.kelempok7.serverapp.service.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService implements GenericService<Role,Integer> {

    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role Id Cannot Found"));
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Integer id, Role role) {
        getById(id);
        role.setId(id);
        return roleRepository.save(role);
    }

    @Override
    public Role delete(Integer id) {
        Role role = getById(id);
        roleRepository.delete(role);
        return role;
    }
}
