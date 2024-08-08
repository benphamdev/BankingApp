package com.banking.thejavabanking.application.mapper;

import com.banking.thejavabanking.domain.authentications.dto.requests.RoleRequest;
import com.banking.thejavabanking.domain.authentications.dto.responses.RoleResponse;
import com.banking.thejavabanking.domain.authentications.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(
            target = "permissions",
            ignore = true
    )
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
