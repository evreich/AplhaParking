package com.client.validator;
 
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import com.client.models.User;
import com.client.response_jsons.UserResponse;
import com.client.utils.AuthUtils;
import com.client.utils.HttpClient;
import com.client.utils.ServerInfo;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
@Component
public class UserValidator implements Validator {

    @Autowired
    HttpClient httpClient;
    @Autowired
    HttpServletRequest httpRequest;
 
    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();
 
    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == User.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
 
        // Check the fields of AppUserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty.appUserForm.login");
        if (user.getId() == 0){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fio", "NotEmpty.appUserForm.fio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
 
        if (!errors.hasFieldErrors("email") && !this.emailValidator.isValid(user.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "Pattern.appUserForm.email");
        }
 
        if (!errors.hasFieldErrors("login")) {
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                return;
            }

            try{
                String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/user/get/" + user.getLogin(), jwt_token);
                UserResponse userResponse = new Gson().fromJson(serverResponse, UserResponse.class);  
                User oldUser = userResponse.user;
                if (oldUser != null && oldUser.getId() != user.getId()) {
                    // Username is not available.
                    errors.rejectValue("login", "Duplicate.appUserForm.login");
                }
            }
            catch (IOException e){
                return;
            }
        }
    }
 
}