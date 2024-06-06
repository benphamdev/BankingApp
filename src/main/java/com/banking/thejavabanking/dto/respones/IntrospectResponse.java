package com.banking.thejavabanking.dto.respones;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IntrospectResponse {
    private boolean valid;
}
