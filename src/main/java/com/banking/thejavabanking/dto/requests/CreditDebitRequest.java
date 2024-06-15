package com.banking.thejavabanking.dto.requests;

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
public class CreditDebitRequest implements Serializable {
    @Size(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    String accountNumber;

    BigDecimal amount;
}
