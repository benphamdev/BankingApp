package com.banking.thejavabanking.mapper;

import com.banking.thejavabanking.dto.requests.RoleRequest;
import com.banking.thejavabanking.dto.respones.RoleResponse;
import com.banking.thejavabanking.models.entity.Role;
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
