package com.banking.thejavabanking.domain.loans.repositories;

import com.banking.thejavabanking.domain.loans.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Integer> {
}
