package com.banking.thejavabanking.domain.notifications.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class TagRequest implements Serializable {
    private String name;
}
