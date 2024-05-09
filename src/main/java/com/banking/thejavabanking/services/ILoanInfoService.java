package com.banking.thejavabanking.services;

import com.banking.thejavabanking.models.entity.LoanInfo;

import java.util.Optional;

public interface ILoanInfoService {
    LoanInfo createLoanInfo(LoanInfo loanInfo);

    Optional<LoanInfo> getLoanInfoById(int id);
}
