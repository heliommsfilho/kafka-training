package com.github.heliommsfilho.kafka.training.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * POJO representing a customer.
 *
 * @author Hélio Márcio Filho
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

    @EqualsAndHashCode.Include
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String name;

    private String email;

    private Integer age;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant registrationDate;
}
