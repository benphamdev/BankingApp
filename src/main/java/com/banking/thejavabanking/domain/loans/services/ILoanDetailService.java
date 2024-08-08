package com.banking.thejavabanking.domain.loans.services;

import com.banking.thejavabanking.domain.accounts.dto.responses.InterestCalculationResponse;
import com.banking.thejavabanking.domain.common.constants.Enums;
import com.banking.thejavabanking.domain.loans.dto.requests.LoanDetailRequest;
import com.banking.thejavabanking.domain.loans.entity.LoanDetail;

import java.util.List;

public interface ILoanDetailService {
    InterestCalculationResponse finalMonthlyAmountIncludingInterest(Integer id);

    LoanDetail saveLoanDetail(LoanDetailRequest loanDetailRequest);

    List<LoanDetail> getLoanDetails();

    LoanDetail getLoanDetailById(Integer id);

    void deleteLoanDetailById(Integer id);

    double calculateInterest(Integer id, Enums.InterestType type);

    void updateLoanDetail(
            LoanDetail loanDetail, Enums.LoanStatus status,
            Enums.LoanPaymentStatus loanPaymentStatus
    );

    void updateLoanDetail(LoanDetail loanDetail, Enums.LoanStatus status);
}
