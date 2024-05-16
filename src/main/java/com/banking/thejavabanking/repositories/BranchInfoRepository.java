package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.BranchInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchInfoRepository extends JpaRepository<BranchInfo, Integer> {
    List<BranchInfo> findByProvinceId(int provinceId);

    @Query("delete from BranchInfo b where b.id = ?1")
    @Modifying
    @Transactional
    void deleteBranchInfoById(Integer id);
}
