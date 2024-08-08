package com.banking.thejavabanking.application.mapper;

import com.banking.thejavabanking.domain.accounts.dto.responses.BranchResponse;
import com.banking.thejavabanking.domain.accounts.entity.BranchInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchResponse toBranchResponse(BranchInfo branchInfo);
}
