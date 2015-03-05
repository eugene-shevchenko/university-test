package com.university.teachers;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import com.university.service.PhilosopherService;
import com.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

/**
 * Created by eshevchenko on 02.03.15.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.university")
public class PhilosophersWebAppConfig {

    @Autowired
    private PhilosopherService philosopherService;

    @Bean
    public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        return new BeanNameUrlHandlerMapping();
    }

    @Bean(name = "/PhilosopherService.json")
    public JsonServiceExporter jsonServiceExporter() {
        JsonServiceExporter exporter = new JsonServiceExporter();
        exporter.setServiceInterface(TeacherService.class);
        exporter.setService(philosopherService);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(PhilosophersWebAppConfig.class, args);
    }
}
