package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class LogoutRequest implements Serializable {
    private String token;
}
