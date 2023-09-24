package com.github.heliommsfilho.kafka.training.spring.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka configuration.
 *
 * @author Hélio Márcio Filho
 */
@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, String> kafkaFactory = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaFactory.setConsumerFactory(createConsumerFactory());

        return kafkaFactory;
    }

    /**
     * Creates a new Kafka consumer factory to read messages.
     *
     * @return The consumer factory.
     */
    @Bean
    public KafkaTemplate<String, String> createKafkaTemplate() {
        return new KafkaTemplate<>(createProducerFactory());
    }

    /**
     * Creates a new Kafka consumer factory to read messages.
     *
     * @return The consumer factory.
     */
    private ConsumerFactory<String, String> createConsumerFactory() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    /**
     * Creates a new Kafka producer factory to read messages.
     *
     * @return The producer factory.
     */
    private ProducerFactory<String, String> createProducerFactory() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new DefaultKafkaProducerFactory<>(properties);
    }
}
