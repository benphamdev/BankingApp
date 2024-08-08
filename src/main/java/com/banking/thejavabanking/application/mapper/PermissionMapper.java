package com.banking.thejavabanking.application.mapper;

import com.banking.thejavabanking.domain.authentications.dto.requests.PermissionRequest;
import com.banking.thejavabanking.domain.authentications.dto.responses.PermissionResponse;
import com.banking.thejavabanking.domain.authentications.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
