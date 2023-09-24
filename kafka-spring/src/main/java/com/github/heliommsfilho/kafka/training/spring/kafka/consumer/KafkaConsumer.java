package com.github.heliommsfilho.kafka.training.spring.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Demo of a simple Kafka comsumer using Spring.
 *
 * @author Hélio Márcio Filho
 */
@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private static final String TOPIC_NAME = "customer.registration";
    private static final String GROUP_ID = "customer-registration-group-id";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void consume(final String message) {
        LOGGER.info("New message received: {}", message);
    }
}
