package com.auth.services;

import com.auth.event_bus_utils.EventUtils;
import com.auth.event_bus_utils.integration_events.UserCreatedIntegrationEvent;
import com.auth.models.User;
import com.auth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventUtils eventUtils;

    public List<User> getUsers(){
        List<User> users = userRepository.getUsers();
        return users;
    }

    public List<String> getLogins(){
        List<String> logins = userRepository.getLogins();
        return logins;
    }

    public User create(User user) throws Exception {
        User createdUser = null;
        createdUser = this.userRepository.create(user);

        // Отправка в очередь RabbitMQ интеграционного события создания нового
        // пользователя
        UserCreatedIntegrationEvent event = new UserCreatedIntegrationEvent(createdUser);
        // TODO: на текущий момент за счет exception в методе при ошибке запроса ивент
        // не будет создаваться и отправлятсья в брокер
        // Возможно стоит переделать
        eventUtils.publish(event);

        return createdUser;
    }

    public User getUserByLogin(String login){
        User user = userRepository.getUserByLogin(login);
        return user;
    }

    public void update(User user){
        userRepository.update(user);
    }

    public void delete(int userId){
        userRepository.delete(userId);
    }

    public boolean isExistsUserRole(int userId, int roleId){
        return userRepository.isExistsUserRole(userId, roleId);
    }

    public void grantRole(int userId, int roleId){
        userRepository.grantRole(userId, roleId);
    }

    public void revokeRole(int userId, int roleId){
        userRepository.revokeRole(userId, roleId);
    }
}