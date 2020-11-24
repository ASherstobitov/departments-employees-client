package com.alexey.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Getter
@Configuration
@EnableWebMvc
@ComponentScan("com.alexey.demo")
@PropertySource({ "classpath:application.properties" })
public class AppConfig {

    @Value("${department.rest.url}")
    public String departmentUrl;

    @Value("${employee.rest.url}")
    public String employeeUrl;


        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
}
