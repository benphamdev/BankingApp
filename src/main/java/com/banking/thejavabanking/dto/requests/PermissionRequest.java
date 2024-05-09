package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PermissionRequest implements Serializable {
    private String name;
    private String description;
}
