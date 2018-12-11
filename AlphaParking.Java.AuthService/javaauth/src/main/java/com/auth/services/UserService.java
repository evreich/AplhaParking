package com.auth.services;

import com.auth.event_bus_utils.EventUtils;
import com.auth.event_bus_utils.integration_events.UserCreatedIntegrationEvent;
import com.auth.models.User;
import com.auth.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventUtils eventUtils;

    public User create(User user) throws JsonProcessingException {
        User createdUser = this.userRepository.create(user);

        // Отправка в очередь RabbitMQ интеграционного события создания нового
        // пользователя
        UserCreatedIntegrationEvent event = new UserCreatedIntegrationEvent(createdUser);
        // TODO: на текущий момент за счет exception в методе при ошибке запроса ивент
        // не будет создаваться и отправлятсья в брокер
        // Возможно стоит переделать
        eventUtils.publish(event);

        return createdUser;
    }
}