package com.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.auth.models.Role;
import com.auth.models.User;
import com.auth.services.RoleService;
import com.auth.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;;

@RestController
public class AuthController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<Role> index() {
        return roleService.getTestData();
    }

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user) throws JsonProcessingException {
        System.out.println(user.toString());
        return userService.create(user);
    }
}