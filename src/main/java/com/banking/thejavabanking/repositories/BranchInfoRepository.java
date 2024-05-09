package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.BranchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchInfoRepository extends JpaRepository<BranchInfo, Integer> {
    List<BranchInfo> findByProvinceId(int provinceId);
}
