package com.client.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.client.models.User;
import com.client.response_jsons.LoginResponse;
import com.client.response_jsons.UsersResponse;
import com.client.utils.AuthUtils;
import com.client.utils.HttpClient;
import com.client.utils.ServerInfo;
import com.client.view_models.UserLoginViewModel;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    HttpClient httpClient;

    @Autowired
    HttpServletRequest httpRequest;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "Pidoras");
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model, HttpServletResponse response) {
        try {
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                AuthUtils.clearJWTCookie(response);
                return "login";
            }
            String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/users", jwt_token);

            UsersResponse usersResponse = new Gson().fromJson(serverResponse, UsersResponse.class);
    
            model.addAttribute("usersList", usersResponse.users);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usersList", new User(0, "login", "password", "fio", "address", "phone", "email"));
        }

        return "users";
    }

    @GetMapping("/login")
    public String login(Model model) {      
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(ModelMap model, @RequestParam String login, @RequestParam String password, HttpServletResponse response) throws IOException {   
        String request = new Gson().toJson(new UserLoginViewModel(login, password));
        String serverResponse = httpClient.postRequest(ServerInfo.SERVER_URL + "/login", request);
        String jwt_token = new Gson().fromJson(serverResponse, LoginResponse.class).access_token;
        AuthUtils.setJWTToCookie(jwt_token, response);
        String username = AuthUtils.getJWTClaims(jwt_token).getSubject();
        model.addAttribute("name", username);
        return new ModelAndView("redirect:/welcome", model);
    }

    @GetMapping("/welcome")
    public String welcome(ModelMap model, @RequestParam String name){
        model.addAttribute("name", name);
        return "welcome";
    }
}