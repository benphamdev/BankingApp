package com.banking.thejavabanking.domain.authentications.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    @NotBlank(message = "INVALID_TOKEN")
    private String token;
}
