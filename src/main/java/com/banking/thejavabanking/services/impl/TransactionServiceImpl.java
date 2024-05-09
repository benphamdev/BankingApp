package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.TransactionDto;
import com.banking.thejavabanking.models.entity.UserTransaction;
import com.banking.thejavabanking.repositories.TransactionRepository;
import com.banking.thejavabanking.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        UserTransaction transaction =
                UserTransaction.builder()
                               .transactionType(transactionDto.getTransactionType())
                               .amount(transactionDto.getAmount())
                               .fromAccount(transactionDto.getFromAccount())
                               .toAccount(transactionDto.getToAccount())
                               .status("SUCCESS")
                               .build();

        // Save transaction to database
        transactionRepository.save(transaction);
    }
}
