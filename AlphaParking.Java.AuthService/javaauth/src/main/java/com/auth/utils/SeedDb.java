package com.auth.utils;

import java.util.Arrays;
import java.util.List;

import com.auth.models.Role;
import com.auth.models.User;
import com.auth.repositories.RoleRepository;
import com.auth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SeedDb {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private List<Role> roles;
    private List<User> users;

    private boolean usersCreated;
    private boolean rolesCreated;

    private final String MANAGER = "manager";
    private final String ADMIN = "admin";
    private final String EMPLOYEE = "employee";

    public SeedDb() {
        roles = Arrays.asList(new Role(1, MANAGER), new Role(2, ADMIN), new Role(3, EMPLOYEE));
        users = Arrays.asList(
                new User(1, MANAGER, MANAGER, "Adminov Manager Adminovich",
                        "Manager st.", "88005553536", "manager@mail.ru"),
                new User(2, ADMIN, ADMIN, "Adminov Admin Adminovich", "Admin st.",
                        "88005553535", "admin@mail.ru"),
                new User(3, EMPLOYEE, EMPLOYEE, "Adminov Employee Adminovich",
                        "Employee st.", "88005553537", "employee@mail.ru"));
        usersCreated = false;
        rolesCreated = false;
    }

    public void seedDefaultRoles() {
        if (this.roleRepository.getRoles().size() == 0) {
            for (Role role : roles) {
                try {
                    this.roleRepository.create(role);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            rolesCreated = true;
        }
    }

    public void seedDefaultUsers() {
        if (this.userRepository.getUsers().size() == 0) {
            for (User user : users) {
                try {
                    this.userRepository.create(user);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            usersCreated = true;
        }
    }

    public void setUserRoleRelationships() {
        if (usersCreated && rolesCreated) {
            this.userRepository.grantRole(
                this.userRepository.getUserByLogin(MANAGER).getId(),
                this.roleRepository.getRoleByName(MANAGER).getId());
            this.userRepository.grantRole(
                this.userRepository.getUserByLogin(ADMIN).getId(),
                this.roleRepository.getRoleByName(ADMIN).getId());
            this.userRepository.grantRole(
                this.userRepository.getUserByLogin(EMPLOYEE).getId(),
                this.roleRepository.getRoleByName(EMPLOYEE).getId());
        }
    }
}