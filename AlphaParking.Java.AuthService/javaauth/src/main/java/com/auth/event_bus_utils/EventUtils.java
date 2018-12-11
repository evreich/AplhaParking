package com.auth.event_bus_utils;

import com.auth.configuraions.RabbitMQConfiguration;
import com.auth.event_bus_utils.integration_events.IntegrationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventUtils {
    @Autowired
    private ObjectMapper objectMapper;
 
    @Autowired
    private RabbitTemplate template;

    public void publish(IntegrationEvent event) throws JsonProcessingException
    {
        String eventJson = objectMapper.writeValueAsString(event);
        Message message = MessageBuilder
                            .withBody(eventJson.getBytes())
                            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                            .build();
        this.template.convertAndSend(RabbitMQConfiguration.exchangeName, "", message);
    }
}