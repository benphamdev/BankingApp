package com.banking.thejavabanking.domain.notifications.dto.responses;

import com.banking.thejavabanking.domain.common.constants.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OtpResponseDto {
    private Enums.OtpStatus status;
    private String message;
}
