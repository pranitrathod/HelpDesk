package com.pranit.helpdesk.config;

import com.pranit.helpdesk.entity.User;
import com.pranit.helpdesk.model.Role;
import com.pranit.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Value("${demo.admin.password:}") private String adminPassword;
    @Value("${demo.agent.password:}") private String agentPassword;

    @Bean
    CommandLineRunner seed(UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            create(repository, encoder, "admin", "admin@helpdesk.local", adminPassword, Role.ADMIN);
            create(repository, encoder, "agent", "agent@helpdesk.local", agentPassword, Role.AGENT);
        };
    }

    private void create(UserRepository repository, PasswordEncoder encoder, String username,
                        String email, String password, Role role) {
        if (password == null || password.isBlank() || repository.findByUsername(username).isPresent()) return;
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        repository.save(user);
    }
}
