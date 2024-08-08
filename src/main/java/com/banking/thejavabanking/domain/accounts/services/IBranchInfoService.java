package com.banking.thejavabanking.domain.accounts.services;

import com.banking.thejavabanking.domain.accounts.dto.requests.BranchInfoRequest;
import com.banking.thejavabanking.domain.accounts.dto.requests.BranchUpdateRequest;
import com.banking.thejavabanking.domain.accounts.dto.responses.BranchResponse;
import com.banking.thejavabanking.domain.accounts.entity.BranchInfo;

import java.util.List;
import java.util.Optional;

public interface IBranchInfoService {
    Integer createBranchInfo(BranchInfoRequest branchInfo);

    void deleteBranchInfo(Integer id);

    List<BranchResponse> getAllBranchInfo();

    Optional<BranchInfo> getBranchInfoById(Integer id);

    List<BranchInfo> getBranchInfoByProvinceId(Integer provinceId);

    void updateBranchInfo(Integer id, BranchUpdateRequest branchUpdateRequest);
}
