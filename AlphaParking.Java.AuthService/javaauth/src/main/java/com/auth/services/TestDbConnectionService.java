package com.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.auth.models.Role;
import com.auth.repositories.RoleRepository;

@Service
public class TestDbConnectionService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getTestData() {  
        return roleRepository.findAll();
    }
}