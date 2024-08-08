package com.banking.thejavabanking.domain.accounts.repositories;

import com.banking.thejavabanking.domain.accounts.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE tbl_account SET balance = ?2 WHERE user_id = ?1",
            nativeQuery = true
    )
    void updateBalance(Integer userId, BigDecimal amount);

    @Query("SELECT a FROM Account a WHERE a.accountNumber = ?1")
    Optional<Account> getAccountByAccountNumber(String accountNumber);
}
