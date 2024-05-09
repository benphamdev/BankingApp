package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class SavingRequest implements Serializable {
    private int userId;
    private BigDecimal amount;
    private int duration;
}
