package com.banking.thejavabanking.domain.loans.repositories;

import com.banking.thejavabanking.domain.loans.entity.LoanDisbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDisbursementRepository extends JpaRepository<LoanDisbursement, Integer> {

}
