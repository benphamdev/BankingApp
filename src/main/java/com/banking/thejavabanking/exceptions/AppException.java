package com.banking.thejavabanking.exceptions;

public class AppException extends RuntimeException {
    ErrorResponse errorResponse;

    public AppException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
