package com.banking.thejavabanking.domain.authentications.services.impl;

import com.banking.thejavabanking.application.mapper.PermissionMapper;
import com.banking.thejavabanking.domain.authentications.dto.requests.PermissionRequest;
import com.banking.thejavabanking.domain.authentications.dto.responses.PermissionResponse;
import com.banking.thejavabanking.domain.authentications.entity.Permission;
import com.banking.thejavabanking.domain.authentications.repositories.PermissionRepository;
import com.banking.thejavabanking.domain.authentications.services.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class PermissionService implements IPermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void deletePermission(String permission) {
        permissionRepository.deleteById(permission);
    }
}
