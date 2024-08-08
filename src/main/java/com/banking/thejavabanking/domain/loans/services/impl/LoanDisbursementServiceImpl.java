package com.banking.thejavabanking.domain.loans.services.impl;

import com.banking.thejavabanking.domain.loans.entity.LoanDisbursement;
import com.banking.thejavabanking.domain.loans.repositories.LoanDisbursementRepository;
import com.banking.thejavabanking.domain.loans.services.ILoanDisbursementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class LoanDisbursementServiceImpl implements ILoanDisbursementService {
    LoanDisbursementRepository loanDisbursementRepository;

    @Override
    public LoanDisbursement createLoanDisbursement(LoanDisbursement loanDisbursement) {
        return loanDisbursementRepository.save(loanDisbursement);
    }

    @Override
    public Optional<LoanDisbursement> getLoanDisbursementById(Integer id) {
        return loanDisbursementRepository.findById(id);
    }

    @Override
    public List<LoanDisbursement> getLoanDisbursements() {
        return loanDisbursementRepository.findAll();
    }

    @Override
    public void deleteLoanDisbursementById(Integer id) {
        loanDisbursementRepository.deleteById(id);
    }
}
