package com.github.heliommsfilho.kafka.training.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.IntStream;

/**
 * Demo of how to create a very simple Kafka producer app.
 *
 * @author Hélio Márcio Filho
 */
public class ProducerApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerApp.class);
    private static final String TOPIC_NAME = "customer.orders";

    public static void main(String[] args) {
        try(final KafkaProducer<String, String> producer = new KafkaProducer<>(getProperties())) {

            IntStream.range(0, 10).forEach(i -> {
                /* If the key is always the same, the messages is sent to the same partition. */
                final String key = String.format("customer-%d", i);
                final ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, key, "order:10€");
                producer.send(producerRecord, messageCallback());
            });
        }
    }

    /**
     * Callback function to be called when a message in produced.
     *
     * @return {@link Callback}.
     */
    private static Callback messageCallback() {
        return (recordMetadata, e) -> {
            if (Objects.nonNull(e)) {
                LOGGER.error("Error when producing message", e);
                return;
            }

            LOGGER.debug("Message successfully published. Partition: {} :: Offset: {}.", recordMetadata.partition(), recordMetadata.offset());
        };
    }

    /**
     * Creates a properties with basic Kafka configs.
     *
     * @return The {@link Properties}.
     */
    private static Properties getProperties() {
        final Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}