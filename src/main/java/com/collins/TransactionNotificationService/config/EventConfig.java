package com.collins.TransactionNotificationService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.collins.TransactionNotificationService.constants.EventConstant.*;

@Configuration
public class EventConfig {

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setConnectionFactory(connectionFactory);
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange tnsExchange(){
        return new TopicExchange(TNS_EXCHANGE);
    }

    @Bean
    public Queue tnsQueue(){
        return new Queue(TNS_QUEUE, true);
    }

    @Bean
    public Binding tnsQueueBinding(){
        return BindingBuilder.bind(tnsQueue()).to(tnsExchange())
                .with(TNS_ROUTING_KEY);
    }

}
