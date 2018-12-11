package com.auth.configuraions;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
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
	static public final String exchangeName = "alphaparking_eventbus_exhange";
	static public final String queueName = "alphaparking_eventbus-queue";

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.fanoutExchange(exchangeName).build();
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}