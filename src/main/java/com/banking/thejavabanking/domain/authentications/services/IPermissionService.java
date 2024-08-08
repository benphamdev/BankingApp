package com.banking.thejavabanking.domain.authentications.services;

import com.banking.thejavabanking.domain.authentications.dto.requests.PermissionRequest;
import com.banking.thejavabanking.domain.authentications.dto.responses.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(String permission);
}
