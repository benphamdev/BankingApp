package com.banking.thejavabanking.mapper;

import com.banking.thejavabanking.dto.respones.BranchResponse;
import com.banking.thejavabanking.models.entity.BranchInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchResponse toBranchResponse(BranchInfo branchInfo);
}
