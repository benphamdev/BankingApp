package com.banking.thejavabanking.domain.loans.services;

import com.banking.thejavabanking.domain.loans.entity.LoanInfo;

import java.util.Optional;

public interface ILoanInfoService {
    LoanInfo createLoanInfo(LoanInfo loanInfo);

    Optional<LoanInfo> getLoanInfoById(int id);
}
