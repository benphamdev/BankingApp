package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.RoleRequest;
import com.banking.thejavabanking.dto.respones.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> findAll();

    void delete(String role);
}
