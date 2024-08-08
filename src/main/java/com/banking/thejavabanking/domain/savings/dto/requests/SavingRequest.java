package com.banking.thejavabanking.domain.savings.dto.requests;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class SavingRequest implements Serializable {
    private int userId;
    private BigDecimal amount;
    private int duration;
}
