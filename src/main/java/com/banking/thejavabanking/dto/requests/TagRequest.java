package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class TagRequest implements Serializable {
    private String name;
}
