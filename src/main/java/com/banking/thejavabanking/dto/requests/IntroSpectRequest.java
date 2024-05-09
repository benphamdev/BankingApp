package com.banking.thejavabanking.dto.requests;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class IntroSpectRequest implements Serializable {
    private String token;
}
