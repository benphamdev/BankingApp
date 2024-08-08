package com.banking.thejavabanking.domain.notifications.dto.requests;

import lombok.Getter;

@Getter
public class OtpValidationRequest {
    private String username;
    private String otpNumber;
}
