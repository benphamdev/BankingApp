package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.PermissionRequest;
import com.banking.thejavabanking.dto.respones.PermissionResponse;
import com.banking.thejavabanking.mapper.PermissionMapper;
import com.banking.thejavabanking.models.entity.Permission;
import com.banking.thejavabanking.repositories.PermissionRepository;
import com.banking.thejavabanking.services.IPermissionService;
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
