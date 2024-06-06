package com.banking.thejavabanking.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IntroSpectRequest implements Serializable {
    @NotBlank(message = "INVALID_TOKEN")
    private String token;
}
