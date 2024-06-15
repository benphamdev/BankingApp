package com.banking.thejavabanking.dto.requests;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class TransferRequest implements Serializable {
    @Size(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    private String fromAccountNumber;

    @Size(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    private String toAccountNumber;

    private BigDecimal amount;
    private String description;
}
