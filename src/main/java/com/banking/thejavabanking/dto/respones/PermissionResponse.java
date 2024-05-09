package com.banking.thejavabanking.dto.respones;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionResponse {
    private String name;
    private String description;
}
