package com.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.auth.models.Role;
import com.auth.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getRoles(){
        List<Role> roles = roleRepository.getRoles();
        return roles;
    }

    public List<String> getRoleNames(){
        List<String> roleNames = roleRepository.getRoleNames();
        return roleNames;
    }

    public Role create(Role role) throws Exception {
        Role createdRole = null;
        createdRole = this.roleRepository.create(role);
        return createdRole;
    }

    public Role getRoleByName(String roleName){
        Role role = roleRepository.getRoleByName(roleName);
        return role;
    }

    public void update(Role role){
        roleRepository.update(role);
    }

    public void delete(int roleId){
        roleRepository.delete(roleId);
    }
}