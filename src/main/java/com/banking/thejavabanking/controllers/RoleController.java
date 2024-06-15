package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.RoleRequest;
import com.banking.thejavabanking.dto.respones.RoleResponse;
import com.banking.thejavabanking.dto.respones.shared.BaseResponse;
import com.banking.thejavabanking.services.impl.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(
        level = PRIVATE,
        makeFinal = true
)
public class RoleController {
    RoleService roleService;

    @PostMapping
    BaseResponse<RoleResponse> createRole(
            @RequestBody RoleRequest roleRequest
    ) {
        return BaseResponse.<RoleResponse>builder()
                           .data(roleService.create(roleRequest))
                           .build();
    }

    @GetMapping
    BaseResponse<List<RoleResponse>> getAllRoles() {
        return BaseResponse.<List<RoleResponse>>builder()
                           .data(roleService.findAll())
                           .build();
    }

    @DeleteMapping("/{role}")
    BaseResponse<Void> deleteRole(
            @PathVariable String role
    ) {
        roleService.delete(role);
        return BaseResponse.<Void>builder()
                           .build();
    }
}
