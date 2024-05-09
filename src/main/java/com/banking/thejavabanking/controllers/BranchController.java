package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.BranchInfoRequest;
import com.banking.thejavabanking.dto.requests.BranchUpdateRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.models.entity.BranchInfo;
import com.banking.thejavabanking.services.impl.BranchInfoServiceImpl;
import com.banking.thejavabanking.services.impl.ProvinceServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.banking.thejavabanking.exceptions.ErrorResponse.BRANCH_NOT_FOUND;
import static com.banking.thejavabanking.exceptions.ErrorResponse.PROVINCE_NOT_FOUND;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
@FieldDefaults(
        makeFinal = true,
        level = lombok.AccessLevel.PRIVATE
)
public class BranchController {
    BranchInfoServiceImpl branchInfoService;
    ProvinceServiceImpl provinceService;

    @GetMapping
    public BaseResponse<List<BranchInfo>> getAllBranches() {
        return BaseResponse.<List<BranchInfo>>builder()
                           .data(branchInfoService.getAllBranchInfo())
                           .message("Branches found")
                           .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<BranchInfo> getBranchById(
            @PathVariable int id
    ) {
        Optional<?> branch = branchInfoService.getBranchInfoById(id);
        if (branch.isEmpty())
            throw new AppException(BRANCH_NOT_FOUND);
        return BaseResponse.<BranchInfo>builder()
                           .data((BranchInfo) branch.get())
                           .message("Branch found")
                           .build();
    }

    @GetMapping("/province")
    public BaseResponse<List<BranchInfo>> getBranchByProvinceId(
            @RequestParam("provinceId") int provinceId
    ) {
        if (provinceService.getProvinceById(provinceId).isEmpty())
            throw new AppException(PROVINCE_NOT_FOUND);
        return BaseResponse.<List<BranchInfo>>builder()
                           .data(branchInfoService.getBranchInfoByProvinceId(provinceId))
                           .message("Branches found")
                           .build();
    }

    // not constraint on province, check if province exists in the service
    @PostMapping("/create")
    public BaseResponse<BranchInfo> createBranch(
            @RequestBody BranchInfoRequest branch
    ) {
        if (provinceService.getProvinceById(branch.getProvinceId()).isEmpty())
            throw new AppException(PROVINCE_NOT_FOUND);

        BranchInfo branchInfo = new BranchInfo();
        branchInfo.setBranchName(branch.getBranchName());
        branchInfo.setAddress(branch.getAddress());
        branchInfo.setProvince(provinceService.getProvinceById(branch.getProvinceId()).get());

        branchInfo = branchInfoService.createBranchInfo(branchInfo);

        branchInfo.getProvince().setName(
                provinceService.getProvinceById(branchInfo.getProvince().getId())
                               .get()
                               .getName());

        return BaseResponse.<BranchInfo>builder()
                           .data(branchInfo)
                           .message("Branch created successfully")
                           .build();
    }

    //    PUT update existing branch
    @PutMapping("/update/{id}")
    public BaseResponse<Void> updateBranchInfo(
            @PathVariable int id,
            @RequestBody BranchUpdateRequest branchInfo
    ) {
        if (branchInfoService.getBranchInfoById(id).isEmpty())
            throw new AppException(BRANCH_NOT_FOUND);
        branchInfoService.updateBranchInfo(id, branchInfo);
        return BaseResponse.<Void>builder()
                           .message("Branch updated successfully")
                           .build();
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<Void> deleteBranch(
            @PathVariable int id
    ) {
        if (branchInfoService.getBranchInfoById(id).isEmpty())
            throw new AppException(BRANCH_NOT_FOUND);

        branchInfoService.deleteBranchInfo(id);
        return BaseResponse.<Void>builder()
                           .message("Branch deleted successfully")
                           .build();
    }
}
