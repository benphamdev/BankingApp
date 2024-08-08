package com.banking.thejavabanking.domain.loans.repositories;

import com.banking.thejavabanking.domain.loans.entity.LoanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDetailRepository extends JpaRepository<LoanDetail, Integer> {
    boolean existsByUserId(int userId);
}
