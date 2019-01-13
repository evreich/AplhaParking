package com.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.auth.models.Role;
import com.auth.repositories.RoleRepository;
import com.auth.utils.AppConsts;

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
        Role createdRole = this.roleRepository.create(role);
        return createdRole;
    }

    public Role getRoleById(int roleId){
        Role role = roleRepository.getRoleById(roleId);
        return role;
    }

    public Role getRoleByName(String roleName){
        Role role = roleRepository.getRoleByName(roleName);
        return role;
    }

    public void update(Role role){
        roleRepository.update(role);
    }

    public void delete(int roleId) throws Exception{
        switch (roleId){
            case AppConsts.ROLE_ADMIN_ID:
                throw new Exception("Запрещено удалять роль администратора");
            case AppConsts.ROLE_MANAGER_ID:
                throw new Exception("Запрещено удалять роль менеджера");
            case AppConsts.ROLE_EMPLOYEE_ID:
                throw new Exception("Запрещено удалять роль работника");
            default:
                roleRepository.delete(roleId);
                break;
        }
    }
}