package com.client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.client.models.Role;
import com.client.models.User;
import com.client.response_jsons.*;
import com.client.utils.AuthUtils;
import com.client.utils.HttpClient;
import com.client.utils.ServerInfo;
import com.client.validator.UserValidator;
import com.client.view_models.UserLoginViewModel;
import com.client.view_models.UserRoleViewMovel;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    HttpClient httpClient;

    @Autowired
    HttpServletRequest httpRequest;

    @Autowired
    private UserValidator userValidator;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == User.class) {
            dataBinder.setValidator(userValidator);
        }
    // ...
    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {      
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(ModelMap model, @RequestParam String login, @RequestParam String password, HttpServletResponse response) throws IOException {   
        String request = new Gson().toJson(new UserLoginViewModel(login, password));
        //!! String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/login", request);
        //!! String jwt_token = new Gson().fromJson(serverResponse, LoginResponse.class).access_token;
        String jwt_token = "test_token"; //!!
        AuthUtils.setJWTToCookie(jwt_token, response);
        //!! String username = AuthUtils.getJWTClaims(jwt_token).getSubject();
        String username = "test_user"; //!!
        model.addAttribute("name", username);
        return new ModelAndView("redirect:/welcome", model);
    }

    @GetMapping("/welcome")
    public String welcome(ModelMap model, @RequestParam String name){
        model.addAttribute("name", name);
        return "welcome";
    }

    @GetMapping("/users")
    public String getUsers(Model model, HttpServletResponse response) {
        try {
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return "login";
            }
            //!! String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/users", jwt_token);
            //!! UsersResponse usersResponse = new Gson().fromJson(serverResponse, UsersResponse.class);
    
            UsersResponse usersResponse = new UsersResponse(); //!!
            usersResponse.users = new ArrayList<User>(); //!!
            usersResponse.users.add( //!!
                new User("test", "testPw", "Тест Тестов", "Тест адрес", "222", "test@yandex.ru")); //!!

            model.addAttribute("usersList", usersResponse.users);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usersList", new User(0, "login", "password", "fio", "address", "phone", "email"));
        }

        return "users";
    }

    @GetMapping("/user/create")
    public String createUser(Model model) { 
        User user = new User();
        model.addAttribute("user", user);
        return "createUser";
    }

    /*
            @RequestParam String login, @RequestParam String password, @RequestParam String fio,
        @RequestParam String address, @RequestParam String phone, @RequestParam String email, 
        */

    @PostMapping("/user/create")
    public String createUser(ModelMap model, @ModelAttribute("user") @Validated User user,
        BindingResult result, final RedirectAttributes redirectAttributes,
        HttpServletResponse response) throws IOException {  
            
        if (result.hasErrors()) {
            return "createUser";
           // return new ModelAndView("redirect:/user/create/");
        }

        try {
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return "redirect:/login";
                //return new ModelAndView("redirect:/login", model);
            }
            //User user = new User(login, password, fio, address, phone, email);
            String request = new Gson().toJson(user);
            //!! String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/user/create", request, jwt_token);
            //!! UserResponse userResponse = new Gson().fromJson(serverResponse, UserResponse.class);

            UserResponse userResponse = new UserResponse(); //!!
            userResponse.user = new User("newTest", "testPw", "Новый Тест Тестов", "Тест адрес", "222", "test@yandex.ru"); //!!

            model.addAttribute("user", userResponse.user);

            redirectAttributes.addFlashAttribute("flashUser", userResponse.user);
            return "redirect:/user/" + userResponse.user.getId();
            //return new ModelAndView("redirect:/user/" + userResponse.user.getId(), model);
        } catch (Exception e) {
            String message = e.getMessage();

            model.addAttribute("error", message);
            return "createUser";
            //return new ModelAndView("redirect:/user/create/");
        }       
    }

    @GetMapping("/user/{userId}")
    public String updateUser(Model model, @PathVariable int userId, HttpServletResponse response) { 
        try{
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return "login";
            }
    
            //!! String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId, jwt_token);
            //!! UserResponse userResponse = new Gson().fromJson(serverResponse, UserResponse.class);  
            UserResponse userResponse = new UserResponse(); //!!
            userResponse.user = new User("newTest", "testPw", "Новый Тест Тестов", "Тест адрес", "222", "test@yandex.ru"); //!! 
            model.addAttribute("user", userResponse.user);
                  
            //!! serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId + "/roles", jwt_token);
            //!! RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);  
            RolesResponse rolesResponse = new RolesResponse(); //!!
            rolesResponse.roles = new ArrayList<Role>(); //!!
            rolesResponse.roles.add(new Role("testRole")); //!!
            model.addAttribute("userRoles", rolesResponse.roles);      
            
            //!! serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            //!! rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);   
            model.addAttribute("allRoles", rolesResponse.roles);  
        } 
        catch (Exception e){
            String message = e.getMessage();
            model.addAttribute("error", message);
        } 
        
        return "updateUser";
    }

    @PostMapping("/user/{userId}")
    public ModelAndView updateUser(ModelMap model, @PathVariable int userId,
        @RequestParam String login, @RequestParam String password, @RequestParam String fio,
        @RequestParam String address, @RequestParam String phone, @RequestParam String email, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
        if(jwt_token == null || jwt_token == ""){
            AuthUtils.clearJWTCookie(response);
            return new ModelAndView("redirect:/login", model);
        }
        User user = new User(userId, login, password, fio, address, phone, email);
        String request = new Gson().toJson(user);
        // !!httpClient.putRequest(ServerInfo.SERVER_URL + "/user/update", request, jwt_token);

        model.addAttribute("user", user);
        
        return new ModelAndView("redirect:/user/" + userId, model);
    }

    @GetMapping("/user/delete/{userId}")
    public ModelAndView deleteUser(ModelMap model, @PathVariable int userId, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
        if(jwt_token == null || jwt_token == ""){
            AuthUtils.clearJWTCookie(response);
            return new ModelAndView("redirect:/login", model);
        }
        
        // !!httpClient.deleteRequest(ServerInfo.SERVER_URL + "/user/delete/" + userId, jwt_token);       
        return new ModelAndView("redirect:/users", model);
    }

    @GetMapping("/roles")
    public String getRoles(Model model, HttpServletResponse response) {
        try {
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return "login";
            }
            // !!String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            // !!RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);

            RolesResponse rolesResponse = new RolesResponse(); //!!
            rolesResponse.roles = new ArrayList<Role>(); //!!
            rolesResponse.roles.add( //!!
                new Role("testRole")); //!!
    
            model.addAttribute("rolesList", rolesResponse.roles);
        } catch (Exception e) {
            String message = e.getMessage();

            model.addAttribute("error", message);
            model.addAttribute("rolesList", new Role(0, "name"));
        }

        return "roles";
    }

    @GetMapping("/role/create")
    public String createRole(Model model) {      
        return "createRole";
    }

    @PostMapping("/role/create")
    public ModelAndView createRole(ModelMap model, 
        @RequestParam String name, HttpServletResponse response) throws IOException {   

        //try {
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return new ModelAndView("redirect:/login", model);
            }
            Role role = new Role(name);
            String request = new Gson().toJson(role);
            // !!String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/role/create", request, jwt_token);
            // !!RoleResponse roleResponse = new Gson().fromJson(serverResponse, RoleResponse.class);

            RoleResponse roleResponse = new RoleResponse(); //!!
            roleResponse.role = new Role("newTestRole"); //!!
        //} catch (Exception e) {
        //    String message = e.getMessage();

         //   model.addAttribute("error", message);
       // }
        
        model.addAttribute("role", roleResponse.role);
        return new ModelAndView("redirect:/role/" + roleResponse.role.getId(), model);
    }

    @GetMapping("/role/{roleId}")
    public String updateRole(Model model, @PathVariable int roleId, HttpServletResponse response) { 
        try{
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return "login";
            }
    
            // !!String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/role/" + roleId, jwt_token);
            // !!RoleResponse roleResponse = new Gson().fromJson(serverResponse, RoleResponse.class);
    
            //List<Role> roles = (List<Role>)new Gson().fromJson(serverResponse, List.class);
           
            RoleResponse roleResponse = new RoleResponse(); //!!
            roleResponse.role = new Role("newTestRole"); //!!
            model.addAttribute("role", roleResponse.role);
    
            //model.addAttribute("name", userId);         
        } 
        catch (Exception e){
            String message = e.getMessage();
            model.addAttribute("error", message);
        } 
        
        return "updateRole";
    }

    @PostMapping("/role/{roleId}")
    public ModelAndView updateRole(ModelMap model, 
        @PathVariable int roleId, @RequestParam String name, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
        if(jwt_token == null || jwt_token == ""){
            AuthUtils.clearJWTCookie(response);
            return new ModelAndView("redirect:/login", model);
        }
        Role role = new Role(roleId, name);
        String request = new Gson().toJson(role);
        // !!httpClient.putRequest(ServerInfo.SERVER_URL + "/role/update", request, jwt_token);

        model.addAttribute("role", role);

        return new ModelAndView("redirect:/role/" + roleId, model);
    }

    @GetMapping("/role/delete/{roleId}")
    public ModelAndView deleteRole(ModelMap model, @PathVariable int roleId, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
        if(jwt_token == null || jwt_token == ""){
            AuthUtils.clearJWTCookie(response);
            return new ModelAndView("redirect:/login", model);
        }
        
        // !!httpClient.deleteRequest(ServerInfo.SERVER_URL + "/role/delete/" + roleId, jwt_token);       
        return new ModelAndView("redirect:/roles", model);
    }

    
    @GetMapping("/user/{userId}/grant")
    public ModelAndView grantRole(ModelMap model, @PathVariable int userId, @RequestParam int grantRoleId, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
        if(jwt_token == null || jwt_token == ""){
            AuthUtils.clearJWTCookie(response);
            return new ModelAndView("redirect:/login", model);
        }
        
        UserRoleViewMovel userRole = new UserRoleViewMovel(userId, grantRoleId);
        String request = new Gson().toJson(userRole);

        // !!httpClient.postRequest(ServerInfo.SERVER_URL + "/user/grant", request, jwt_token);       
        return new ModelAndView("redirect:/user/" + userId, model);
    }

    
    @GetMapping("/user/{userId}/revoke/{roleId}")
    public ModelAndView revokeRole(ModelMap model, @PathVariable int userId, @PathVariable int roleId, 
        HttpServletResponse response) throws IOException {  

            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return new ModelAndView("redirect:/login", model);
            }
            
            UserRoleViewMovel userRole = new UserRoleViewMovel(userId, roleId);
            String request = new Gson().toJson(userRole);
    
            // !!httpClient.postRequest(ServerInfo.SERVER_URL + "/user/revoke", request, jwt_token);       
            return new ModelAndView("redirect:/user/" + userId, model);
    }
}