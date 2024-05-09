package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

@Getter
public class OtpValidationRequest {
    private String username;
    private String otpNumber;
}
