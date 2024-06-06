package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.BranchInfoRequest;
import com.banking.thejavabanking.dto.requests.BranchUpdateRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.dto.respones.BranchResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.models.entity.BranchInfo;
import com.banking.thejavabanking.services.impl.BranchInfoServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.banking.thejavabanking.exceptions.ErrorResponse.BRANCH_NOT_FOUND;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
@FieldDefaults(
        makeFinal = true,
        level = lombok.AccessLevel.PRIVATE
)
@Slf4j
public class BranchController {
    BranchInfoServiceImpl branchInfoService;

    @GetMapping
    public BaseResponse<List<BranchResponse>> getAllBranches() {
        log.info("Getting all branches");
        return BaseResponse.<List<BranchResponse>>builder()
                           .message("List of branches")
                           .data(branchInfoService.getAllBranchInfo())
                           .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<BranchInfo> getBranchById(
            @PathVariable @Min(1) int id
    ) {
        Optional<?> branch = branchInfoService.getBranchInfoById(id);

        if (branch.isEmpty())
            throw new AppException(BRANCH_NOT_FOUND);

        log.info("Getting branch by id: {}", id);
        return BaseResponse.<BranchInfo>builder()
                           .data((BranchInfo) branch.get())
                           .message("Branch found")
                           .build();
    }

    @GetMapping("/province")
    public BaseResponse<List<BranchInfo>> getBranchByProvinceId(
            @RequestParam("provinceId") @Min(1) int provinceId
    ) {

        log.info("Getting branches by province id: {}", provinceId);

        return BaseResponse.<List<BranchInfo>>builder()
                           .data(branchInfoService.getBranchInfoByProvinceId(provinceId))
                           .message("Branches found")
                           .build();
    }

    // not constraint on province, check if province exists in the service
    @PostMapping("/create")
    public BaseResponse<Integer> createBranch(
            @RequestBody BranchInfoRequest branch
    ) {
        Integer ans = branchInfoService.createBranchInfo(branch);
        return BaseResponse.<Integer>builder()
                           .data(ans)
                           .message("Branch created successfully")
                           .build();
    }

    //    PUT update existing branch
    @PutMapping("/update/{id}")
    public BaseResponse<Void> updateBranchInfo(
            @PathVariable @Min(1) int id,
            @RequestBody BranchUpdateRequest branchInfo
    ) {
        if (branchInfoService.getBranchInfoById(id)
                             .isEmpty())
            throw new AppException(BRANCH_NOT_FOUND);
        branchInfoService.updateBranchInfo(id, branchInfo);
        return BaseResponse.<Void>builder()
                           .message("Branch updated successfully")
                           .build();
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<Void> deleteBranch(
            @PathVariable @Min(1) int id
    ) {
        if (branchInfoService.getBranchInfoById(id)
                             .isEmpty())
            throw new AppException(BRANCH_NOT_FOUND);

        branchInfoService.deleteBranchInfo(id);
        return BaseResponse.<Void>builder()
                           .message("Branch deleted successfully")
                           .build();
    }
}
