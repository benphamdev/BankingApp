package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.LoanDetailRequest;
import com.banking.thejavabanking.dto.respones.InterestCalculationResponse;
import com.banking.thejavabanking.models.Enums;
import com.banking.thejavabanking.models.entity.LoanDetail;

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
