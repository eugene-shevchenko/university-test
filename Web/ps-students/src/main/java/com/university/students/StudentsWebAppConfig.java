package com.university.students;

import com.googlecode.jsonrpc4j.spring.JsonProxyFactoryBean;
import com.university.service.PhilosopherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by eshevchenko on 02.03.15.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.university")
public class StudentsWebAppConfig {

    @Value("${url.philosophers}")
    private String philosophersUrl;

    @Bean
    @Primary
    public JsonProxyFactoryBean jsonProxyFactoryBean() {
        JsonProxyFactoryBean proxyFactoryBean = new JsonProxyFactoryBean();
        proxyFactoryBean.setServiceUrl(philosophersUrl + "/PhilosopherService.json");
        proxyFactoryBean.setServiceInterface(PhilosopherService.class);
        return proxyFactoryBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentsWebAppConfig.class, args);
    }
}
