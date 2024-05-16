package com.banking.thejavabanking.dto.requests;

import com.banking.thejavabanking.dto.validators.PhoneNumberConstraint;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class TransferRequest implements Serializable {
    @PhoneNumberConstraint(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    private String fromAccountNumber;

    @PhoneNumberConstraint(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    private String toAccountNumber;

    private BigDecimal amount;
    private String description;
}
