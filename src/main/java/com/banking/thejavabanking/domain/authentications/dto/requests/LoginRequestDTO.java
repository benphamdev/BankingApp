package com.banking.thejavabanking.domain.authentications.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginRequestDTO implements Serializable {
    @Email(message = "INVALID_EMAIL")
    private String email;

    @Size(
            min = 6,
            message = "PASSWORD_INVALID"
    )
    private String password;
}
