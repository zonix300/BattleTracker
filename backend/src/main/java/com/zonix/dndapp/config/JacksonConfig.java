package com.zonix.dndapp.config;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ParameterNamesModule parameterNamesModule() {
        return new ParameterNamesModule();
    }
}
