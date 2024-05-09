package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Integer> {
}
