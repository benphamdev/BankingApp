package com.banking.thejavabanking.domain.authentications.dto.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {
    private boolean authenticated;
    private String token;
}
