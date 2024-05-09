package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
public class PostCreationRequest implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private String name;
    private String content;
    private List<Integer> listTagId;
}
