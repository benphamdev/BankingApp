package com.banking.thejavabanking.exceptions;

public class AppException extends RuntimeException {
    EnumsErrorResponse errorResponse;

    public AppException(EnumsErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public EnumsErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(EnumsErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
