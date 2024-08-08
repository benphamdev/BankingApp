package com.banking.thejavabanking.domain.authentications.dto.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IntrospectResponse {
    private boolean valid;
}
