package com.auth.configuraions;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import com.auth.utils.AppConsts;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

@Configuration
public class RabbitMQConfiguration  
{
    @Bean
    Queue queue() {
        return QueueBuilder.durable(AppConsts.queueName).build();
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.fanoutExchange(AppConsts.exchangeName).build();
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}