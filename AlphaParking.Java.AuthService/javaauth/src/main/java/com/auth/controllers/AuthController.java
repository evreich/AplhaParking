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
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
        try {
        this.jsonObject = objectMapper.createObjectNode();    
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
    public ResponseEntity<ObjectNode> login(@RequestBody TokenViewModel viewModel) {
        try {
        this.jsonObject = objectMapper.createObjectNode();      
            String token = authService.getToken(viewModel.login, viewModel.pass, null);
            this.jsonObject.put("access_token", token);
            // !!!!!!!!внесены изменения
            ObjectNode node = objectMapper.valueToTree(userService.getUserByLogin(viewModel.login));
            this.jsonObject.putObject("user").putAll(node);
            // !!!!!!!
            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);            
        } catch (EmptyResultDataAccessException e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ObjectNode> register(@RequestBody User user) {
        try {
        this.jsonObject = objectMapper.createObjectNode();     
            User newUser = userService.create(user);
            String token = authService.getToken(newUser.getLogin(), newUser.getPassword(), null);
            // !!!!!!!!внесены изменения
            ObjectNode node = objectMapper.valueToTree(newUser);
            this.jsonObject.putObject("user").putAll(node);
            // !!!!!!!
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
        try{   
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
        } catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logins")
    public ResponseEntity<ObjectNode> getLogins() {
        try{   
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/roles")
    // Не делать проверку токена на метод либо сделать какой-то специальный токен для общения 
    // по HTTP между сервисами
    public ResponseEntity<ObjectNode> getRoles() {
        try{
            this.jsonObject = objectMapper.createObjectNode();    
            //Проверка, что запрос создан зарегистрированным ранее пользователем 
            // (имеется подписанный данным сервером JWT token в запросе)
            ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
            if(authError != null){
                return authError;
            }
            
            ArrayNode node = objectMapper.valueToTree( roleService.getRoles() );
            this.jsonObject.putArray("roles").addAll(node);

            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/roleNames")
    public ResponseEntity<ObjectNode> getRoleNames() {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/create")
    public ResponseEntity<ObjectNode> createUser(@RequestBody User user) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
  
    @GetMapping("/user/{userId}")
    public ResponseEntity<ObjectNode> getUserById(@PathVariable int userId) {
        try{  
            this.jsonObject = objectMapper.createObjectNode();      
            //Проверка, что запрос создан зарегистрированным ранее пользователем 
            // (имеется подписанный данным сервером JWT token в запросе)
            ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
            if(authError != null){
                return authError;
            }

            User user = userService.getUserById(userId);
            if (user != null){
                ObjectNode node = objectMapper.valueToTree(user);
                this.jsonObject.putObject("user").putAll(node);
            }
            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/get/{login}")
    public ResponseEntity<ObjectNode> getUserByLogin(@PathVariable String login) {
        try{  
            this.jsonObject = objectMapper.createObjectNode();      
            //Проверка, что запрос создан зарегистрированным ранее пользователем 
            // (имеется подписанный данным сервером JWT token в запросе)
            ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
            if(authError != null){
                return authError;
            }

            User user = userService.getUserByLogin(login);
            if (user != null){
                ObjectNode node = objectMapper.valueToTree(user);
                this.jsonObject.putObject("user").putAll(node);
            }
            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<ObjectNode> updateUser(@RequestBody User user) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<ObjectNode> deleteUser(@PathVariable int userId) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/role/create")
    public ResponseEntity<ObjectNode> createRole(@RequestBody Role role) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<ObjectNode> getRoleById(@PathVariable int roleId) {
        try{  
            this.jsonObject = objectMapper.createObjectNode();      
            //Проверка, что запрос создан зарегистрированным ранее пользователем 
            // (имеется подписанный данным сервером JWT token в запросе)
            ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
            if(authError != null){
                return authError;
            }

            Role role = roleService.getRoleById(roleId);
            if (role != null){
                ObjectNode node = objectMapper.valueToTree(role);
                this.jsonObject.putObject("role").putAll(node);
            }
            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/role/get/{roleName}")
    public ResponseEntity<ObjectNode> getRoleByName(@PathVariable String roleName) {
        try{  
            this.jsonObject = objectMapper.createObjectNode();      
            //Проверка, что запрос создан зарегистрированным ранее пользователем 
            // (имеется подписанный данным сервером JWT token в запросе)
            ResponseEntity<ObjectNode> authError = AuthFilter.authRequestFilter(request);
            if(authError != null){
                return authError;
            }

            Role role = roleService.getRoleByName(roleName);

            if (role != null){
                ObjectNode node = objectMapper.valueToTree(role);
                this.jsonObject.putObject("role").putAll(node);
            }

            return new ResponseEntity<ObjectNode>(this.jsonObject, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/role/update")
    public ResponseEntity<ObjectNode> updateRole(@RequestBody Role role) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/role/delete/{roleId}")
    public ResponseEntity<ObjectNode> deleteRole(@PathVariable int roleId) {
        try{
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
        catch(Exception e){
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }  

    @GetMapping("/user/{userId}/roles")
    // Не делать проверку токена на метод либо сделать какой-то специальный токен для общения 
    // по HTTP между сервисами
    public ResponseEntity<ObjectNode> getUserRoles(@PathVariable int userId) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/user/{userId}/role/{roleId}")
    public ResponseEntity<ObjectNode> isExistsUserRole(@PathVariable int userId, @PathVariable int roleId) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/grant")
    public ResponseEntity<ObjectNode>  grantRole(@RequestBody UserRoleViewModel userRole) {
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/revoke")
    public ResponseEntity<ObjectNode> revokeRole(@RequestBody UserRoleViewModel userRole) {    
        try{  
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
        catch (Exception e) {
            return new ResponseError().generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }   
    }
}