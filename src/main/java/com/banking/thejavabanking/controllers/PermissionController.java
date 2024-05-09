package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.PermissionRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.dto.respones.PermissionResponse;
import com.banking.thejavabanking.services.impl.PermissionService;
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

    @PostMapping
    BaseResponse<PermissionResponse> createPermission(
            @RequestBody PermissionRequest permissionRequest
    ) {
        return BaseResponse.<PermissionResponse>builder()
                           .data(permissionService.createPermission(permissionRequest))
                           .build();
    }

    @GetMapping
    BaseResponse<List<PermissionResponse>> apiResponse() {
        return BaseResponse.<List<PermissionResponse>>builder()
                           .data(permissionService.getAllPermissions())
                           .build();
    }

    @DeleteMapping("/{permissionId}")
    BaseResponse<Void> deletePermission(
            @PathVariable String permissionId
    ) {
        permissionService.deletePermission(permissionId);
        return BaseResponse.<Void>builder().build();
    }

}
