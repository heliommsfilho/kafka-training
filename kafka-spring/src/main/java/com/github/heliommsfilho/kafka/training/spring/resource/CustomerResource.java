package com.github.heliommsfilho.kafka.training.spring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.heliommsfilho.kafka.training.spring.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

/**
 * Simple endpoint to simulate a new customer registration.
 *
 * @author Hélio Márcio Filho
 */
@RestController
@RequestMapping("/customer")
public class CustomerResource {

    private static final String TOPIC_NAME = "customer.registration";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public CustomerResource(final KafkaTemplate<String, String> kafkaTemplate, final ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customer.setId(UUID.randomUUID());
        customer.setRegistrationDate(Instant.now());

        kafkaTemplate.send(TOPIC_NAME, serialize(customer));

        return ResponseEntity
                .created(URI.create(String.format("customer/%s", customer.getId())))
                .body(customer);
    }

    /**
     * Try to serialize the object in to a JSON.
     *
     * @param object The object to serialize.
     * @return The JSON.
     */
    private String serialize(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (final Exception e) {
            throw new RuntimeException("Error when trying to serialize the object.", e);
        }
    }
}
