package com.hrs.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {

    @Bean
    @Primary
    public ObjectMapper customJson () {
        return new Jackson2ObjectMapperBuilder ()
                .indentOutput (true)
                .serializationInclusion (JsonInclude.Include.NON_EMPTY)
                .serializationInclusion (JsonInclude.Include.NON_NULL)
                .serializationInclusion (JsonInclude.Include.NON_ABSENT)
                .autoDetectGettersSetters (true)
                .build ()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    }
}
