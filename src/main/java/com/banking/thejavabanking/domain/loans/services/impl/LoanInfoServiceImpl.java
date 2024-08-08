package com.banking.thejavabanking.domain.loans.services.impl;

import com.banking.thejavabanking.domain.loans.entity.LoanInfo;
import com.banking.thejavabanking.domain.loans.repositories.LoanInfoRepository;
import com.banking.thejavabanking.domain.loans.services.ILoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanInfoServiceImpl implements ILoanInfoService {
    @Autowired
    private LoanInfoRepository loanInfoRepository;

    @Override
    public LoanInfo createLoanInfo(LoanInfo loanInfo) {
        return loanInfoRepository.save(loanInfo);
    }

    @Override
    public Optional<LoanInfo> getLoanInfoById(int id) {
        return loanInfoRepository.findById(id);
    }
}
