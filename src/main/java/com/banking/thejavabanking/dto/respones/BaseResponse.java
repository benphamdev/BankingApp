package com.banking.thejavabanking.dto.respones;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    @Builder.Default
    int status = 1000;
    String message;
    T data;

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse(T data) {
        this.data = data;
    }
}
