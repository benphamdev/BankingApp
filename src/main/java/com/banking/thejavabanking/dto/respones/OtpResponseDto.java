package com.banking.thejavabanking.dto.respones;

import com.banking.thejavabanking.models.Enums;
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
