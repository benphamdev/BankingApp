package com.banking.thejavabanking.domain.authentications.services.impl;

import com.banking.thejavabanking.application.mapper.RoleMapper;
import com.banking.thejavabanking.domain.authentications.dto.requests.RoleRequest;
import com.banking.thejavabanking.domain.authentications.dto.responses.RoleResponse;
import com.banking.thejavabanking.domain.authentications.repositories.PermissionRepository;
import com.banking.thejavabanking.domain.authentications.repositories.RoleRepository;
import com.banking.thejavabanking.domain.authentications.services.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(
        level = PRIVATE,
        makeFinal = true
)
public class RoleService implements IRoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {

        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll()
                             .stream()
                             .map(roleMapper::toRoleResponse)
                             .toList();
    }

    @Override
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
