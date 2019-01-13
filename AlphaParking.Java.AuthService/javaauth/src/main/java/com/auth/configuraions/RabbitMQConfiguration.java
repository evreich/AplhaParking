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
    Queue queueAdd() {
        return QueueBuilder.durable(AppConsts.queueNameAdd).build();
    }

    @Bean
    Exchange exchangeAdd() {
        return ExchangeBuilder.fanoutExchange(AppConsts.topicExchangeNameAdd).build();
    }

    @Bean
    Binding bindingAdd() {
        return BindingBuilder.bind(queueAdd()).to((FanoutExchange) exchangeAdd());
    }

    @Bean
    Queue queueEdit() {
        return QueueBuilder.durable(AppConsts.queueNameEdit).build();
    }

    @Bean
    Exchange exchangeEdit() {
        return ExchangeBuilder.fanoutExchange(AppConsts.topicExchangeNameEdit).build();
    }

    @Bean
    Binding bindingEdit() {
        return BindingBuilder.bind(queueEdit()).to((FanoutExchange) exchangeEdit());
    }

    @Bean
    Queue queueDelete() {
        return QueueBuilder.durable(AppConsts.queueNameDelete).build();
    }

    @Bean
    Exchange exchangeDelete() {
        return ExchangeBuilder.fanoutExchange(AppConsts.topicExchangeNameDelete).build();
    }

    @Bean
    Binding bindingDelete() {
        return BindingBuilder.bind(queueDelete()).to((FanoutExchange) exchangeDelete());
    }
}