package com.client.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.client.models.Role;
import com.client.models.User;
import com.client.response_jsons.*;
import com.client.utils.AuthUtils;
import com.client.utils.HttpClient;
import com.client.utils.ServerInfo;
import com.client.utils.UserService;
import com.client.validator.RoleValidator;
import com.client.validator.UserValidator;
import com.client.view_models.UserLoginViewModel;
import com.client.view_models.UserRoleViewMovel;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private RoleValidator roleValidator;

    private UserService userService;

    private String getToken(Model model, HttpServletResponse response){
        boolean isLogin;         
        String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
        if(jwt_token == null || jwt_token == ""){
            AuthUtils.clearJWTCookie(response);
            isLogin = false;
        }else{
            isLogin = true;
        }
        model.addAttribute("isLogin", isLogin);

        return jwt_token;
    }

    private String getToken(ModelMap model, HttpServletResponse response){
        boolean isLogin;         
        String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
        if(jwt_token == null || jwt_token == ""){
            AuthUtils.clearJWTCookie(response);
            isLogin = false;
        }else{
            isLogin = true;
        }
        model.addAttribute("isLogin", isLogin);

        return jwt_token;
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        Class<?> targetClass = target.getClass();
        if (targetClass == User.class) {
            dataBinder.setValidator(userValidator);
        }else if (targetClass == Role.class){
            dataBinder.setValidator(roleValidator);
        }
    }

    @GetMapping("/")
    public String index(Model model, HttpServletResponse response) throws Exception {
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }
        return "redirect:/welcome?name=" + AuthUtils.getJWTClaims(jwt_token).getSubject();
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletResponse response) {      
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletResponse response) {  
        AuthUtils.clearJWTCookie(response);
        return "login";    
    }

    @PostMapping("/login")
    public String login(ModelMap model, @RequestParam String login, @RequestParam String password, HttpServletResponse response) throws IOException {   
        String request = new Gson().toJson(new UserLoginViewModel(login, password));
        String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/login", request);

        LoginResponse loginResponse = new Gson().fromJson(serverResponse, LoginResponse.class);
        if (loginResponse.error != null){
            model.addAttribute("error", loginResponse.error);
            return "login";
        }

        String jwt_token = loginResponse.access_token;

        AuthUtils.setJWTToCookie(jwt_token, response);
        String username = AuthUtils.getJWTClaims(jwt_token).getSubject();
        model.addAttribute("name", username);
        return "redirect:/welcome";        
    }

    @GetMapping("/welcome")
    public String welcome(ModelMap model, @RequestParam String name, HttpServletResponse response){
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        model.addAttribute("name", name);
        return "welcome";
    }

    @GetMapping("/usersAll")
    public String getUsers(Model model, HttpServletResponse response) throws IOException {
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/users", jwt_token);
        UsersResponse usersResponse = new Gson().fromJson(serverResponse, UsersResponse.class);

        if (usersResponse.error != null){
            model.addAttribute("error", usersResponse.error);
            return "usersAll";
        }

        model.addAttribute("usersList", usersResponse.users);

        return "usersAll";
    }

    @GetMapping("/users")
    public String getUsers(Model model,
        @RequestParam("page") Optional<Integer> page, 
        @RequestParam("size") Optional<Integer> size,
        HttpServletResponse response) throws IOException {

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/users", jwt_token);
        UsersResponse usersResponse = new Gson().fromJson(serverResponse, UsersResponse.class);

        if (usersResponse.error != null){
            model.addAttribute("error", usersResponse.error);
            return "users";
        }

        userService = new UserService(usersResponse.users);

        Page<User> userPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("userPage", userPage);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "users";
    }

    @GetMapping("/user/create")
    public String createUser(Model model,  HttpServletResponse response) { 
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        User user = new User();
        model.addAttribute("user", user);
        return "createUser";
    }

    @PostMapping("/user/create")
    public String createUser(ModelMap model, @ModelAttribute("user") @Validated User user,
        BindingResult result, final RedirectAttributes redirectAttributes,
        HttpServletResponse response) throws IOException {  

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "createUser";
        }

        String request = new Gson().toJson(user);
        //!!String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/user/create", request, jwt_token);
        String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/user", request, jwt_token);
        UserResponse userResponse = new Gson().fromJson(serverResponse, UserResponse.class);

        if (userResponse.error != null){
            model.addAttribute("error", userResponse.error);
            return "createUser";
        }

        model.addAttribute("user", userResponse.user);

        return "redirect:/user/" + userResponse.user.getId();    
    }

    @GetMapping("/user/{userId}")
    public String updateUser(Model model, @PathVariable int userId, HttpServletResponse response) throws IOException { 
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId, jwt_token);
        UserResponse userResponse = new Gson().fromJson(serverResponse, UserResponse.class); 
        
        if (userResponse.error != null){
            model.addAttribute("error", userResponse.error);
            model.addAttribute("user", new User());
            return "updateUser";
        }
        
        model.addAttribute("user", userResponse.user);
                
        serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId + "/roles", jwt_token);
        RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class); 

        model.addAttribute("userRoles", rolesResponse.roles);      
        
        serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
        rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);  

        model.addAttribute("allRoles", rolesResponse.roles);  
        
        return "updateUser";
    }

    @PostMapping("/user/{userId}")
    public String updateUser(ModelMap model, @PathVariable int userId,
        @ModelAttribute("user") @Validated User user,
        BindingResult result, final RedirectAttributes redirectAttributes,
        HttpServletResponse response) throws IOException {  

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId + "/roles", jwt_token);
            RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);  
            model.addAttribute("userRoles", rolesResponse.roles);      
            
            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);   
            model.addAttribute("allRoles", rolesResponse.roles);

            return "updateUser";
        }

        String request = new Gson().toJson(user);
        //!!String serverResponse = httpClient.putRequest(ServerInfo.SERVER_URL + "/user/update", request, jwt_token);
        String serverResponse = httpClient.putRequest(ServerInfo.SERVER_URL + "/user", request, jwt_token);  
        ErrorResponse errorResponse = new Gson().fromJson(serverResponse, ErrorResponse.class);
        if (errorResponse.error != null){
            model.addAttribute("error", errorResponse.error);

            model.addAttribute("user", user);

            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId + "/roles", jwt_token);
            RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);  
            model.addAttribute("userRoles", rolesResponse.roles);      
            
            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);   
            model.addAttribute("allRoles", rolesResponse.roles);

            return "roles";
        }
        
        return "redirect:/user/" + userId;
    }

    @PostMapping("/user/delete/{userId}")
    public String deleteUser(ModelMap model, @PathVariable int userId, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }
        
        //!!String serverResponse = httpClient.deleteRequest(ServerInfo.SERVER_URL + "/user/delete/" + userId, jwt_token); 
        String serverResponse = httpClient.deleteRequest(ServerInfo.SERVER_URL + "/user/" + userId, jwt_token); 
        ErrorResponse errorResponse = new Gson().fromJson(serverResponse, ErrorResponse.class);
        if (errorResponse.error != null){
            model.addAttribute("error", errorResponse.error);

            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            UsersResponse usersResponse = new Gson().fromJson(serverResponse, UsersResponse.class);
    
            if (usersResponse.error != null){
                model.addAttribute("error", usersResponse.error);
                return "users";
            }
    
            model.addAttribute("usersList", usersResponse.users);

            return "users";
        }      
        return "redirect:/users";
    }

    @GetMapping("/roles")
    public String getRoles(Model model, HttpServletResponse response) throws IOException {
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
        RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);

        if (rolesResponse.error != null){
            model.addAttribute("error", rolesResponse.error);
            return "roles";
        }

        model.addAttribute("rolesList", rolesResponse.roles);

        return "roles";
    }

    @GetMapping("/role/create")
    public String createRole(Model model, HttpServletResponse response) { 
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }
        
        Role role = new Role();
        model.addAttribute("role", role);    
        return "createRole";
    }

    @PostMapping("/role/create")
    public String createRole(ModelMap model, @ModelAttribute("role") @Validated Role role,
    BindingResult result, final RedirectAttributes redirectAttributes,
    HttpServletResponse response) throws IOException {   

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "createRole";
        }

        String request = new Gson().toJson(role);
        //!!String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/role/create", request, jwt_token);
        String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/role", request, jwt_token);
        RoleResponse roleResponse = new Gson().fromJson(serverResponse, RoleResponse.class);

        if (roleResponse.error != null){
            model.addAttribute("error", roleResponse.error);
            return "createRole";
        }
        model.addAttribute("role", roleResponse.role);
        return "redirect:/role/" + roleResponse.role.getId();
    }

    @GetMapping("/role/{roleId}")
    public String updateRole(Model model, @PathVariable int roleId, HttpServletResponse response) throws IOException { 
        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/role/" + roleId, jwt_token);
        RoleResponse roleResponse = new Gson().fromJson(serverResponse, RoleResponse.class);

        if (roleResponse.error != null){
            model.addAttribute("error", roleResponse.error);
            model.addAttribute("role", new Role());
            return "updateRole";
        }

        model.addAttribute("role", roleResponse.role);      
        
        return "updateRole";
    }

    @PostMapping("/role/{roleId}")
    public String updateRole(ModelMap model, 
        @PathVariable int roleId, @ModelAttribute("role") @Validated Role role,
        BindingResult result, final RedirectAttributes redirectAttributes, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "updateRole";
        }

        String request = new Gson().toJson(role);
        //!!String serverResponse = httpClient.putRequest(ServerInfo.SERVER_URL + "/role/update", request, jwt_token);
        String serverResponse = httpClient.putRequest(ServerInfo.SERVER_URL + "/role", request, jwt_token);
        ErrorResponse errorResponse = new Gson().fromJson(serverResponse, ErrorResponse.class);
        if (errorResponse.error != null){
            model.addAttribute("error", errorResponse.error);
            model.addAttribute("role", role);
            return "roles";
        }

        model.addAttribute("role", role);

        return "redirect:/role/" + roleId;
    }

    @PostMapping("/role/delete/{roleId}")
    public String deleteRole(ModelMap model, @PathVariable int roleId, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }
        
        //!!String serverResponse = httpClient.deleteRequest(ServerInfo.SERVER_URL + "/role/delete/" + roleId, jwt_token); 
        String serverResponse = httpClient.deleteRequest(ServerInfo.SERVER_URL + "/role/" + roleId, jwt_token); 
        ErrorResponse errorResponse = new Gson().fromJson(serverResponse, ErrorResponse.class);
        if (errorResponse.error != null){
            model.addAttribute("error", errorResponse.error);

            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);
    
            if (rolesResponse.error != null){
                model.addAttribute("error", rolesResponse.error);
                return "roles";
            }
    
            model.addAttribute("rolesList", rolesResponse.roles);

            return "roles";
        } 
             
        return "redirect:/roles";
    }

    
    @PostMapping("/user/{userId}/grant")
    public String grantRole(ModelMap model, @PathVariable int userId, @RequestParam int grantRoleId, 
        HttpServletResponse response) throws IOException {   

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }
        
        UserRoleViewMovel userRole = new UserRoleViewMovel(userId, grantRoleId);
        String request = new Gson().toJson(userRole);

        String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/user/grant", request, jwt_token);        
        ErrorResponse errorResponse = new Gson().fromJson(serverResponse, ErrorResponse.class);
        if (errorResponse.error != null){
            model.addAttribute("error", errorResponse.error);

            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId, jwt_token);
            UserResponse userResponse = new Gson().fromJson(serverResponse, UserResponse.class);  
            model.addAttribute("user", userResponse.user); 

            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId + "/roles", jwt_token);
            RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);  
            model.addAttribute("userRoles", rolesResponse.roles);      
            
            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);   
            model.addAttribute("allRoles", rolesResponse.roles);

            return "updateUser";
        } 

        return "redirect:/user/" + userId;
    }

    
    @PostMapping("/user/{userId}/revoke/{roleId}")
    public String revokeRole(ModelMap model, @PathVariable int userId, @PathVariable int roleId, 
        HttpServletResponse response) throws IOException {  

        String jwt_token = getToken(model, response);
        if (jwt_token == "" || jwt_token == null){
            return "redirect:/login";
        }
        
        UserRoleViewMovel userRole = new UserRoleViewMovel(userId, roleId);
        String request = new Gson().toJson(userRole);

        String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/user/revoke", request, jwt_token);  
        ErrorResponse errorResponse = new Gson().fromJson(serverResponse, ErrorResponse.class);
        if (errorResponse.error != null){
            model.addAttribute("error", errorResponse.error);

            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId, jwt_token);
            UserResponse userResponse = new Gson().fromJson(serverResponse, UserResponse.class);  
            model.addAttribute("user", userResponse.user); 

            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/" + userId + "/roles", jwt_token);
            RolesResponse rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);  
            model.addAttribute("userRoles", rolesResponse.roles);      
            
            serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/roles", jwt_token);
            rolesResponse = new Gson().fromJson(serverResponse, RolesResponse.class);   
            model.addAttribute("allRoles", rolesResponse.roles);

            return "updateUser";
        }      
        return "redirect:/user/" + userId;
    }
}