package com.banking.thejavabanking.domain.notifications.dto.requests;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDetailRequest {
    @Email(message = "INVALID_EMAIL")
    String recipient;

    String message;

    String subject;

    String attachment;
}
