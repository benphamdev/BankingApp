package com.banking.thejavabanking.dto.requests;

import com.banking.thejavabanking.dto.validators.PhoneNumberConstraint;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class EnquiryRequest implements Serializable {
    @PhoneNumberConstraint(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    private String accountNumber;
}
