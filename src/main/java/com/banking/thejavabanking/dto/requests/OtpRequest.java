package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OtpRequest implements Serializable {
    private String phoneNumber;
    private String username;
}
