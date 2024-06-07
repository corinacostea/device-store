package com.device.store.config;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration(JacksonAutoConfiguration.class)
public class ObjectMapperConfig {
    @Bean
    public JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }
}