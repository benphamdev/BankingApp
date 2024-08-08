package com.banking.thejavabanking.domain.notifications.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OtpRequest implements Serializable {
    private String phoneNumber;
    private String username;
}
