package com.banking.thejavabanking.domain.loans.repositories;

import com.banking.thejavabanking.domain.loans.entity.LoanInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanInfoRepository extends JpaRepository<LoanInfo, Integer> {

}
