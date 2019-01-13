package com.client.validator;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.client.models.Role;
import com.client.response_jsons.RoleResponse;
import com.client.utils.AuthUtils;
import com.client.utils.HttpClient;
import com.client.utils.ServerInfo;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
@Component
public class RoleValidator implements Validator {

    @Autowired
    HttpClient httpClient;
    @Autowired
    HttpServletRequest httpRequest;
 
    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Role.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        Role role = (Role) target;

        // Check the fields of AppRoleForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.appRoleForm.name");

        if (!errors.hasFieldErrors("name")) {
            String jwt_token = AuthUtils.getJWTFromCookie(httpRequest);
            if(jwt_token == null || jwt_token == ""){
                return;
            }

            try{
                String serverResponse = httpClient.getRequest(ServerInfo.SERVER_URL + "/role/get/" + role.getName(), jwt_token);
                RoleResponse roleResponse = new Gson().fromJson(serverResponse, RoleResponse.class);  
                Role oldRole = roleResponse.role;
                if (oldRole != null && oldRole.getId() != role.getId()) {
                    // roleName is not available.
                    errors.rejectValue("name", "Duplicate.appRoleForm.name");
                }
            }
            catch (IOException e){
                return;
            }
        }
    }
 
}