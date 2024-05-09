package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.LoanInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanInfoRepository extends JpaRepository<LoanInfo, Integer> {

}
