package com.ptit.hotelmanagementsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hotel Management API", version = "1.0", description = "API for managing a hotel"))
public class HotelManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagementSystemApplication.class, args);
    }

}
