package com.university;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * Created by eshevchenko on 03.03.15.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ServiceConfig {

    @Bean
    public List<String> maleNames() throws IOException {
        return parseJsonArray(String.class, "names-male.json");
    }

    @Bean
    public List<String> femaleNames() throws IOException {
        return parseJsonArray(String.class, "names-female.json");
    }

    private static <T> List<T> parseJsonArray(Class<T> elClass, String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return (List<T>) new ObjectMapper().readValue(resource.getInputStream(), new TypeReference<List<T>>() { });
    }
}
