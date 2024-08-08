package com.banking.thejavabanking.contract.abstractions.shared.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ErrorResponse implements Serializable {
    @Builder.Default
    private Date timestamp = new Date();
    private int status;
    private String path;
    private String error;
    private String message;
}
