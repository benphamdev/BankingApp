package com.banking.thejavabanking.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LogoutRequest implements Serializable {
    @NotBlank(message = "INVALID_TOKEN")
    private String token;
}
