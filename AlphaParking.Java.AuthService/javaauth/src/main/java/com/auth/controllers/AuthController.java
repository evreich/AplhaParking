package com.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.auth.services.AuthService;
import com.auth.models.Role;
import com.auth.models.User;
import com.auth.services.RoleService;
import com.auth.services.UserService;
import com.auth.utils.AuthFilter;
import com.auth.utils.ResponseError;
import com.auth.view_models.TokenVKViewModel;
import com.auth.view_models.TokenViewModel;
import com.auth.view_models.UserRoleViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class AuthController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    private ObjectNode jsonObject;

    @CrossOrigin(origins = "http://localhost:8383")
    @PostMapping("/vk/auth")
    public ResponseEntity<ObjectNode> vkLogin(@RequestBody String code) throws IOException {
        this.jsonObject = objectMapper.createObjectNode();
        try {
            TokenVKViewModel token = authService.getTokenVK(code);

            ObjectNode node = objectMapper.valueToTree( token );

            this.jsonObject.putObject("vkAuthInfo").putAll(node); 

            User user = userService.getUserByVkToken(token);
            if (user != null){
                String appToken = authService.getToken(user.getLogin(), user.getPassword(), token);
                this.jsonObject.put("access_token", appToken);
            }

            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);            
        }
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<ObjectNode> login(@RequestBody TokenViewModel viewModel) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();
        try {
            String token = authService.getToken(viewModel.login, viewModel.pass, null);
            this.jsonObject.put("access_token", token);
            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);            
        } catch (EmptyResultDataAccessException e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectNode> register(@RequestBody User user) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();
        try {
            User newUser = userService.create(user);
            String token = authService.getToken(newUser.getLogin(), newUser.getPassword(), null);
            this.jsonObject.put("userId", newUser.getId());
            this.jsonObject.put("access_token", token);
            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);            
        } catch (EmptyResultDataAccessException e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ObjectNode> getUsers() {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ArrayNode node = objectMapper.valueToTree( userService.getUsers() );
        this.jsonObject.putArray("users").addAll(node);

        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @GetMapping("/logins")
    public ResponseEntity<ObjectNode> getLogins() {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ArrayNode node = objectMapper.valueToTree( userService.getLogins() );
        this.jsonObject.putArray("logins").addAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @GetMapping("/roles")
    // Не делать проверку токена на метод либо сделать какой-то специальный токен для общения 
    // по HTTP между сервисами
    public ResponseEntity<ObjectNode> getRoles() {
        this.jsonObject = objectMapper.createObjectNode();     
        ArrayNode node = objectMapper.valueToTree( roleService.getRoles() );
        this.jsonObject.putArray("roles").addAll(node);

        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @GetMapping("/roleNames")
    public ResponseEntity<ObjectNode> getRoleNames() {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ArrayNode node = objectMapper.valueToTree( roleService.getRoleNames() );
        this.jsonObject.putArray("roleNames").addAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @PostMapping("/user/create")
    public ResponseEntity<ObjectNode> createUser(@RequestBody User user) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ObjectNode node = objectMapper.valueToTree( userService.create(user) );
        this.jsonObject.putObject("user").putAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }
  
    @GetMapping("/user/{userId}")
    public ResponseEntity<ObjectNode> getUserById(@PathVariable int userId) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ObjectNode node = objectMapper.valueToTree( userService.getUserById(userId) );
        this.jsonObject.putObject("user").putAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @GetMapping("/user/get/{login}")
    public ResponseEntity<ObjectNode> getUserByLogin(@PathVariable String login) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ObjectNode node = objectMapper.valueToTree( userService.getUserByLogin(login) );
        this.jsonObject.putObject("user").putAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @PutMapping("/user/update")
    public ResponseEntity<ObjectNode> updateUser(@RequestBody User user) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }
        userService.update(user);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);   
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<ObjectNode> deleteUser(@PathVariable int userId) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }
        userService.delete(userId);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);     
    }


    @PostMapping("/role/create")
    public ResponseEntity<ObjectNode> createRole(@RequestBody Role role) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ObjectNode node = objectMapper.valueToTree( roleService.create(role) );
        this.jsonObject.putObject("role").putAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<ObjectNode> getRoleById(@PathVariable int roleId) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ObjectNode node = objectMapper.valueToTree( roleService.getRoleById(roleId) );
        this.jsonObject.putObject("role").putAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @GetMapping("/role/get/{name}")
    public ResponseEntity<ObjectNode> getRoleByName(@PathVariable String roleName) throws Exception {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ObjectNode node = objectMapper.valueToTree( roleService.getRoleByName(roleName) );
        this.jsonObject.putObject("role").putAll(node);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @PutMapping("/role/update")
    public ResponseEntity<ObjectNode> updateRole(@RequestBody Role role) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }
        roleService.update(role);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);      
    }

    @DeleteMapping("/role/delete/{roleId}")
    public ResponseEntity<ObjectNode> deleteRole(@PathVariable int roleId) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }
        roleService.delete(roleId);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);     
    }  

    @GetMapping("/user/{userId}/roles")
    // Не делать проверку токена на метод либо сделать какой-то специальный токен для общения 
    // по HTTP между сервисами
    public ResponseEntity<ObjectNode> getUserRoles(@PathVariable int userId) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        ArrayNode node = objectMapper.valueToTree( userService.getUserRoles(userId) );
        this.jsonObject.putArray("roles").addAll(node);

        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }


    @GetMapping("/user/{userId}/role/{roleId}")
    public ResponseEntity<ObjectNode> isExistsUserRole(@PathVariable int userId, @PathVariable int roleId) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }

        this.jsonObject.put("isExistsUserRole", userService.isExistsUserRole(userId, roleId));
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
    }

    @PostMapping("/user/grant")
    public ResponseEntity<ObjectNode>  grantRole(@RequestBody UserRoleViewModel userRole) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }
        userService.grantRole(userRole.userId, userRole.roleId);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);    
    }

    @PostMapping("/user/revoke")
    public ResponseEntity<ObjectNode> revokeRole(@RequestBody UserRoleViewModel userRole) {
        this.jsonObject = objectMapper.createObjectNode();      
        //Проверка, что запрос создан зарегистрированным ранее пользователем 
        // (имеется подписанный данным сервером JWT token в запросе)
        ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
        if(authError != null){
            return authError;
        }
        userService.revokeRole(userRole.userId, userRole.roleId);
        return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);      
    }
}