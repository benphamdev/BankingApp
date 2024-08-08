package com.banking.thejavabanking.application.configuration;

import com.banking.thejavabanking.domain.common.constants.Enums;
import com.banking.thejavabanking.domain.users.entities.User;
import com.banking.thejavabanking.domain.users.repositories.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.twilio.Twilio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Set;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Value("${twilio.AccountSID}")
    private String AccountSID;
    @Value("${twilio.AuthToken}")
    private String AuthToken;
    @Value("${twilio.phoneNumber}")
    private String phoneNumber;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByEmail("admin")
                              .isEmpty()) {
                var roles = Set.of(Enums.Role.ADMIN.name());

                User admin = User.builder()
                                 .firstName("Admin")
                                 .email("admin")
                                 .password(passwordEncoder.encode("123456789"))
                                 .gender(Enums.Gender.MALE)
//                                 .roles(roles)
                                 .build();

                userRepository.save(admin);
                log.warn("Admin user created with default password: 123456789");
            }
        };
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource(
                "firebase-notification.json").getInputStream());
        FirebaseOptions options = FirebaseOptions.builder()
                                                 .setCredentials(credentials)
                                                 .build();
        FirebaseApp app = FirebaseApp.initializeApp(options, "the-java-banking");
        return FirebaseMessaging.getInstance(app);
    }

    @Bean
    public void setUp() {
        Twilio.init(AccountSID, AuthToken);
    }

}
