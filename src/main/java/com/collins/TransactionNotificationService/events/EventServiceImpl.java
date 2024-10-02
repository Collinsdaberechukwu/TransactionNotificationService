//package com.collins.TransactionNotificationService.events;
//
//import com.collins.TransactionNotificationService.constants.EventConstant;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.MessagePostProcessor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class EventServiceImpl implements EventService{
//
//    private final RabbitTemplate rabbitTemplate;
//
//    @Override
//    public void sendTNSMessage(Object data, String routingKey){
//        log.info("Sending message to queue");
//        MessagePostProcessor messagePostProcessor = message -> {
//            message.getMessageProperties().setHeader(AmqpHeaders.DELIVERY_TAG, System.currentTimeMillis());
//            return message;
//        };
//        rabbitTemplate.convertAndSend(EventConstant.TNS_EXCHANGE,
//                routingKey, data, messagePostProcessor);
//        log.info("Message sent successfully");
//    }
//}
