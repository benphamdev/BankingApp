package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<UserTransaction, String> {
    @Query(
            value = "SELECT * FROM user_transaction WHERE from_account = ?1 AND created_at BETWEEN ?2 AND ?3",
            nativeQuery = true
    )
    List<UserTransaction> findByFromAccountAndCreatedAtBetween(
            String accountNumber, LocalDate startDate, LocalDate endDate
    );
}

