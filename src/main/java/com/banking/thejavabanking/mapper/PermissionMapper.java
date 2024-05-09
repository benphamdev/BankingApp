package com.banking.thejavabanking.mapper;

import com.banking.thejavabanking.dto.requests.PermissionRequest;
import com.banking.thejavabanking.dto.respones.PermissionResponse;
import com.banking.thejavabanking.models.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
