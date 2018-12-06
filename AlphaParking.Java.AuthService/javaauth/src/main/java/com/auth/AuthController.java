package com.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.auth.models.Role;
import com.auth.services.TestDbConnectionService;;

@RestController
public class AuthController {

    @Autowired
    TestDbConnectionService service;

    @RequestMapping("/")
    public List<Role> index() {
        return service.getTestData();
    }
}