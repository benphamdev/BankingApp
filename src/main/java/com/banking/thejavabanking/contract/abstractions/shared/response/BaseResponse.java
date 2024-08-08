package com.banking.thejavabanking.contract.abstractions.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class BaseResponse<T> implements Serializable {
    int status;
    String message;
    @NonFinal
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    /**
     * Response data when the API executes successfully or getting error. For PUT, PATCH, DELETE
     *
     * @param status
     * @param message
     */
    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
