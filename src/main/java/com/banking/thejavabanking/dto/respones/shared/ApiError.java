package com.banking.thejavabanking.dto.respones.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
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