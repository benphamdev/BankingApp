package com.banking.thejavabanking.dto.respones;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IntrospectResponse {
    private boolean valid;
}
