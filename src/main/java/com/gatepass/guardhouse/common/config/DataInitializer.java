package com.gatepass.guardhouse.common.config;

import com.gatepass.guardhouse.user.model.User;
import com.gatepass.guardhouse.user.model.UserRole;
import com.gatepass.guardhouse.user.model.UserStatus;
import com.gatepass.guardhouse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.seed.admin.password}")
    private String seedAdminPassword;
    @Value("${app.seed.admin.email}")
    private String seedAdminEmail;

    @Override
    public void run(String... args) {

        if (userRepository.existsByUserRole(UserRole.ADMIN)) {
            log.info("Admin user already exists");
            return;
        }

        User admin = User.builder()
                .name("Super Admin")
                .email(seedAdminEmail)
                .password(passwordEncoder.encode(seedAdminPassword))
                .userRole(UserRole.ADMIN)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(admin);
        log.info("Admin user created: {}", admin.getEmail());
    }
}
