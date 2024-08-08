package com.banking.thejavabanking.domain.accounts.dto.requests;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDto implements Serializable {
    @Size(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    String fromAccount;

    @Size(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    String toAccount;

    BigDecimal amount;

    String description;
}
