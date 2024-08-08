package com.banking.thejavabanking.contract.abstractions.shared.response;

//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ApiError {
    static ApiError createFieldApiError(
            String field, String message, String path, Object rejectedValue
    ) {
        return new ApiErrorField(field, message, path, rejectedValue);
    }

    String message();

    record ApiErrorField(
            String field, String message, String path, Object rejectedValue
    ) implements ApiError {
    }
}