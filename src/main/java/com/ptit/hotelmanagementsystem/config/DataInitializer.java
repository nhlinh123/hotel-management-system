package com.ptit.hotelmanagementsystem.config;

import com.ptit.hotelmanagementsystem.model.User;
import com.ptit.hotelmanagementsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setRoles("ROLE_ADMIN");
                userRepository.save(admin);
                System.out.println("Created default admin user.");
            }
        };
    }
}
