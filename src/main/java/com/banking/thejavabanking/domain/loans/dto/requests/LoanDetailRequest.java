package com.banking.thejavabanking.domain.loans.dto.requests;

import com.banking.thejavabanking.domain.loans.entity.LoanInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoanDetailRequest implements Serializable {
    private String referenceNumber;
    private int userId;
    private LoanInfo loanInfo;
}
