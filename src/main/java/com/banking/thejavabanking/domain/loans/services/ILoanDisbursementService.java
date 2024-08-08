package com.banking.thejavabanking.domain.loans.services;

import com.banking.thejavabanking.domain.loans.entity.LoanDisbursement;

import java.util.List;
import java.util.Optional;

public interface ILoanDisbursementService {
    LoanDisbursement createLoanDisbursement(LoanDisbursement loanDisbursement);

    Optional<LoanDisbursement> getLoanDisbursementById(Integer id);

    List<LoanDisbursement> getLoanDisbursements();

    void deleteLoanDisbursementById(Integer id);
}
