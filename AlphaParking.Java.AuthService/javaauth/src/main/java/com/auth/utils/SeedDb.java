package com.auth.utils;

import java.util.Arrays;
import java.util.List;

import com.auth.models.Role;
import com.auth.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeedDb {
    @Autowired
    private RoleRepository repository;

    private List<Role> roles;

    public SeedDb() {
        roles = Arrays.asList(new Role(1, "employee"), new Role(2, "manager"));
    }

    public void seedDefaultRoles() {
        if (this.repository.getRoles().size() == 0) {
            for (Role role : roles) {
                try {
                    this.repository.create(role);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } 
        }
    }
}