package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.BranchUpdateRequest;
import com.banking.thejavabanking.models.entity.BranchInfo;

import java.util.List;
import java.util.Optional;

public interface IBranchInfoService {
    BranchInfo createBranchInfo(BranchInfo branchInfo);

    void deleteBranchInfo(int id);

    List<BranchInfo> getAllBranchInfo();

    Optional<BranchInfo> getBranchInfoById(int id);

    List<BranchInfo> getBranchInfoByProvinceId(int provinceId);

    void updateBranchInfo(int id, BranchUpdateRequest branchUpdateRequest);
}
