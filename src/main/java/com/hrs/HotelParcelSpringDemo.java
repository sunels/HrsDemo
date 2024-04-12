package com.hrs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Hotel Parcel Demo Application ", version = "1.0.0",
        description = "Hotel Guest Parcel Management Rest API Documentation v1.0"))
@EnableWebMvc
public class HotelParcelSpringDemo {

    public static void main (String[] args) {
        SpringApplication.run (HotelParcelSpringDemo.class, args);
    }

}