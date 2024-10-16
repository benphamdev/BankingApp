package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.respones.shared.PageResponse;
import com.banking.thejavabanking.models.entity.UserTransaction;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionService {
    void saveTransaction(UserTransaction userTransaction);

    List<UserTransaction> transactionList();

    List<UserTransaction> getTransactionWithAccountID(Integer accountID);

    List<UserTransaction> getTransactions(
            String accountNumber, LocalDate startDate, LocalDate endDate
    );

    void exportTransactionPdf(
            String accountNumber, LocalDate startDate, LocalDate endDate
    );

    PageResponse<?> getAllTransactionWithBySort(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllTransactionCurrentUserWithBySort(
            Integer accountId, int pageNo, int pageSize, String sortBy
    );
}
