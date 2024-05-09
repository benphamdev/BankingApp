package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.TransactionDto;

public interface ITransactionService {
    void saveTransaction(TransactionDto transaction);
}
