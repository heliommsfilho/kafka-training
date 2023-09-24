package com.github.heliommsfilho.kafka.training.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Demo of how to create a very simple Kafka consumer app.
 *
 * @author Hélio Márcio Filho
 */
public class ConsumerApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApp.class);
    private static final String TOPIC_NAME = "customer.orders";

    public static void main(String[] args) {
        try(final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getProperties())) {
            consumer.subscribe(Collections.singleton(TOPIC_NAME));

            while (true) {
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                records.forEach(r -> {
                    LOGGER.debug("New Order received. Details:");
                    LOGGER.debug("Key: {}", r.key());
                    LOGGER.debug("Value: {}", r.value());
                    LOGGER.debug("Offset: {}", r.offset());
                    LOGGER.debug("Partition: {}.", r.partition());
                });
            }
        }
    }

    /**
     * Creates a properties with basic Kafka configs.
     *
     * @return The {@link Properties}.
     */
    private static Properties getProperties() {
        final Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "customer-orders-group-id");

        return properties;
    }
}
