package com.auth.services;

import com.auth.event_bus_utils.EventUtils;
import com.auth.event_bus_utils.integration_events.UserCreatedIntegrationEvent;
import com.auth.event_bus_utils.integration_events.UserEditedIntegrationEvent;
import com.auth.event_bus_utils.integration_events.UserRemovedIntegrationEvent;
import com.auth.models.Role;
import com.auth.models.User;
import com.auth.repositories.UserRepository;
import com.auth.utils.AppConsts;
import com.auth.view_models.TokenVKViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventUtils eventUtils;

    public User getUserByVkToken(TokenVKViewModel tokenVk) throws Exception {
        User user = userRepository.getUserByVkToken(tokenVk);
        if (user == null) {
            return user;
        }

        this.grantRole(user.getId(), AppConsts.ROLE_EMPLOYEE_ID);
        // Отправка в очередь RabbitMQ интеграционного события создания нового
        // пользователя
        UserCreatedIntegrationEvent event = new UserCreatedIntegrationEvent(user);
        // TODO: на текущий момент за счет exception в методе при ошибке запроса ивент
        // не будет создаваться и отправлятсья в брокер
        // Возможно стоит переделать
        try {
            eventUtils.publish(event, AppConsts.topicExchangeNameAdd);

        } catch (Exception e) {
        }

        return user;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.getUsers();
        return users;
    }

    public List<String> getLogins() {
        List<String> logins = userRepository.getLogins();
        return logins;
    }

    public User create(User user) throws Exception {
        User createdUser = null;
        createdUser = this.userRepository.create(user);
        if (createdUser == null) {
            return createdUser;
        }

        this.grantRole(createdUser.getId(), AppConsts.ROLE_EMPLOYEE_ID);
        // Отправка в очередь RabbitMQ интеграционного события создания нового
        // пользователя
        UserCreatedIntegrationEvent event = new UserCreatedIntegrationEvent(createdUser);
        // TODO: на текущий момент за счет exception в методе при ошибке запроса ивент
        // не будет создаваться и отправлятсья в брокер
        // Возможно стоит переделать
        try {
            eventUtils.publish(event, AppConsts.topicExchangeNameAdd);

        } catch (Exception e) {
        }

        return createdUser;
    }

    public User getUserById(int userId) {
        User user = userRepository.getUserById(userId);
        return user;
    }

    public User getUserByLogin(String login) {
        User user = userRepository.getUserByLogin(login);
        return user;
    }

    public void update(User user) {
        User currUser = userRepository.getUserById(user.getId());
        userRepository.update(user);

        // Отправка в очередь RabbitMQ интеграционного события создания нового
        // пользователя
        UserEditedIntegrationEvent event = new UserEditedIntegrationEvent(user, currUser);
        // TODO: на текущий момент за счет exception в методе при ошибке запроса ивент
        // не будет создаваться и отправлятсья в брокер
        // Возможно стоит переделать
        try {
            eventUtils.publish(event, AppConsts.topicExchangeNameEdit);

        } catch (Exception e) {
        }
    }

    public void delete(int userId) {
        User user = userRepository.getUserById(userId);

        userRepository.delete(userId);
        // Отправка в очередь RabbitMQ интеграционного события создания нового
        // пользователя
        UserRemovedIntegrationEvent event = new UserRemovedIntegrationEvent(user);
        // TODO: на текущий момент за счет exception в методе при ошибке запроса ивент
        // не будет создаваться и отправлятсья в брокер
        // Возможно стоит переделать

        try {
            eventUtils.publish(event, AppConsts.topicExchangeNameDelete);

        } catch (Exception e) {
        }
    }

    public List<Role> getUserRoles(int userId) {
        return userRepository.getUserRoles(userId);
    }

    public boolean isExistsUserRole(int userId, int roleId) {
        return userRepository.isExistsUserRole(userId, roleId);
    }

    public void grantRole(int userId, int roleId) {
        userRepository.grantRole(userId, roleId);
    }

    public void revokeRole(int userId, int roleId) {
        userRepository.revokeRole(userId, roleId);
    }
}