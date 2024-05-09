package com.banking.thejavabanking.dto.requests;

import com.banking.thejavabanking.models.entity.LoanInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoanDetailRequest implements Serializable {
    private String referenceNumber;
    private int userId;
    private LoanInfo loanInfo;
}
