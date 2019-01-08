package com.client.validator;
 
import com.client.models.User;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
@Component
public class UserValidator implements Validator {
 
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fio", "NotEmpty.appUserForm.fio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
 
        if (!errors.hasFieldErrors("email") && !this.emailValidator.isValid(user.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "Pattern.appUserForm.email");
        }
 
        if (!errors.hasFieldErrors("login")) {
            User oldUser = new User(); // !! Здесь должен быть get запрос на сервак. getUserByLogin
            if (oldUser != null) {
                // Username is not available.
                errors.rejectValue("login", "Duplicate.appUserForm.login");
            }
        }
    }
 
}