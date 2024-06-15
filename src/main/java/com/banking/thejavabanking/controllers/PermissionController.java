package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.PermissionRequest;
import com.banking.thejavabanking.dto.respones.PermissionResponse;
import com.banking.thejavabanking.dto.respones.shared.BaseResponse;
import com.banking.thejavabanking.services.impl.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @Operation(
            summary = "Create Permission",
            description = "Create a new permission"
    )
    @PostMapping
    BaseResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        return BaseResponse.<PermissionResponse>builder()
                           .message("Permission created successfully")
                           .data(permissionService.createPermission(permissionRequest))
                           .build();
    }

    @Operation(
            summary = "Get All Permissions",
            description = "Get all permissions"
    )
    @GetMapping
    BaseResponse<List<PermissionResponse>> apiResponse() {
        return BaseResponse.<List<PermissionResponse>>builder()
                           .message("List of all permissions")
                           .data(permissionService.getAllPermissions())
                           .build();
    }

    @Operation(
            summary = "Delete Permission",
            description = "Delete a permission"
    )
    @DeleteMapping("/{permissionId}")
    BaseResponse<Void> deletePermission(@PathVariable String permissionId) {
        permissionService.deletePermission(permissionId);
        return BaseResponse.<Void>builder()
                           .message("Permission deleted successfully")
                           .build();
    }
}
