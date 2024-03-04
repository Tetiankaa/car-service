package com.example.service;


import com.example.orderservice.dto.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreatedEventConsumer implements MessageListener<Integer, OrderCreatedEvent> {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "order-creation-events-consumer-1")
    @Override
    public void onMessage(ConsumerRecord<Integer, OrderCreatedEvent> data) {
     log.info("Successfully created order: {}",data.value().getOrderId());
    }
}
