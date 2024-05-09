package com.banking.thejavabanking.services;

import com.banking.thejavabanking.models.entity.LoanDisbursement;

import java.util.List;
import java.util.Optional;

public interface ILoanDisbursementService {
    LoanDisbursement createLoanDisbursement(LoanDisbursement loanDisbursement);

    Optional<LoanDisbursement> getLoanDisbursementById(Integer id);

    List<LoanDisbursement> getLoanDisbursements();

    void deleteLoanDisbursementById(Integer id);
}
