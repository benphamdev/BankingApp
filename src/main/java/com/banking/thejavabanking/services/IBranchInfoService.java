package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.BranchInfoRequest;
import com.banking.thejavabanking.dto.requests.BranchUpdateRequest;
import com.banking.thejavabanking.dto.respones.BranchResponse;
import com.banking.thejavabanking.models.entity.BranchInfo;

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
