package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.PermissionRequest;
import com.banking.thejavabanking.dto.respones.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(String permission);
}
