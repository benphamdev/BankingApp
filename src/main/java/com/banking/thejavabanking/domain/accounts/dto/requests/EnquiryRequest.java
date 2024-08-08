package com.banking.thejavabanking.domain.accounts.dto.requests;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class EnquiryRequest implements Serializable {
    @Size(
            min = 10,
            message = "INVALID_ACCOUNT_NUMBER"
    )
    private String accountNumber;
}
