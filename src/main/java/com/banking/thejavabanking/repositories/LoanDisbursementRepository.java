package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.LoanDisbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDisbursementRepository extends JpaRepository<LoanDisbursement, Integer> {

}
