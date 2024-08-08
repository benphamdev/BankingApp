package com.banking.thejavabanking.domain.authentications.services;

import com.banking.thejavabanking.domain.authentications.dto.requests.RoleRequest;
import com.banking.thejavabanking.domain.authentications.dto.responses.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> findAll();

    void delete(String role);
}
