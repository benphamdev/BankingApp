package com.banking.thejavabanking.dto.requests;

import com.banking.thejavabanking.dto.validators.PhoneNumberConstraint;
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
    @PhoneNumberConstraint(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    String fromAccount;

    @PhoneNumberConstraint(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    String toAccount;

    BigDecimal amount;

    String description;
}
