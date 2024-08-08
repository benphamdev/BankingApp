package com.banking.thejavabanking.application.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties("twilio")
@Data
public class TwilioConfig {
    private String AccountSID;
    private String AuthToken;
    private String phoneNumber;
}
