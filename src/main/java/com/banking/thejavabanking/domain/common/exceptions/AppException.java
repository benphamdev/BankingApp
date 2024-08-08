package com.banking.thejavabanking.domain.common.exceptions;

import com.banking.thejavabanking.domain.common.constants.EnumsErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    EnumsErrorResponse errorResponse;

    public AppException(EnumsErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}
