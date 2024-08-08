package com.banking.thejavabanking.domain.authentications.dto.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionResponse {
    private String name;
    private String description;
}
