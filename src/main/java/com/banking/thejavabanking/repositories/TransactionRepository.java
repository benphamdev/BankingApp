package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.Account;
import com.banking.thejavabanking.models.entity.UserTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<UserTransaction, String> {
    @Query(
            value = "SELECT * FROM tbl_user_transaction WHERE from_account = ?1 AND DATE (created_at) BETWEEN ?2 AND ?3",
            nativeQuery = true
    )
    List<UserTransaction> findByFromAccountAndCreatedAtBetween(
            String accountNumber, LocalDate startDate, LocalDate endDate
    );

    @Query(
            value = "SELECT * FROM tbl_user_transaction WHERE account_id = ?1",
            nativeQuery = true
    )
    List<UserTransaction> findUserTransactionByAccountId(Integer accountId);

    Page<UserTransaction> findUserTransactionByAccount(Account account, Pageable pageable);
}

