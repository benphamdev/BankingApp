package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.UserCreationRequest;
import com.banking.thejavabanking.dto.requests.UserUpdateRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.dto.respones.UserResponse;
import com.banking.thejavabanking.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@Tag(
        name = "User Account API",
        description = "User API"
)
@Slf4j
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {this.userService = userService;}

    @Operation(
            summary = "Create Account",
            description = "Create a new account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account created successfully"
    )
    @PostMapping(value = "/signup")
    public BaseResponse<Long> addUser(
            @RequestBody @Valid UserCreationRequest userRequest
    ) {
        log.info(
                "Request to create a new account , {}, {}",
                userRequest.getFirstName(),
                userRequest.getLastName()
        );
        long userId = userService.createUser(userRequest);
        return new BaseResponse<>(HttpStatus.CREATED.value(), "Account created successfully", userId);
    }

    @PutMapping("/{userId}")
    public BaseResponse<Void> updateUser(
            @PathVariable("userId") @Min(1) Integer id,
            @RequestBody UserUpdateRequest user
    ) {
        userService.updateUser(id, user);
        return new BaseResponse<>(HttpStatus.OK.value(), "User updated successfully");
    }

    @GetMapping("/myProfile")
    public BaseResponse<UserResponse> getMyProfile() {
        return BaseResponse.<UserResponse>builder()
                           .message("Profile retrieved successfully")
                           .data(userService.getMyProfile())
                           .build();
    }

    @PostMapping("/update-avatar")
    public BaseResponse<UserResponse> updateProfile(
            @RequestParam("user_id") @Min(1) int userId,
            @RequestBody MultipartFile profilePicture
    ) throws IOException {
        return BaseResponse.<UserResponse>builder()
                           .message("Profile picture updated successfully")
                           .data(userService.updateProfile(userId, profilePicture))
                           .build();
    }

    @GetMapping("/{userId}")
    public BaseResponse<Void> updatePhoneToken(
            @PathVariable("userId") int userId,
            @RequestParam(value = "phoneToken", required = false) String phoneToken
    ) {
        userService.updatePhoneToken(userId, phoneToken);
        return BaseResponse.<Void>builder()
                           .message("Phone token updated successfully")
                           .build();
    }

    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteUser(
            @PathVariable("userId") @Min(1) int id
    ) {
        userService.deleteUser(id);
        return BaseResponse.<String>builder()
                           .message("User deleted successfully")
                           .build();
    }

    @Operation(
            summary = "Get list users per pageNo",
            description = "Send a request via this API to get user list by pageNo and pageSize"
    )
    @GetMapping("/list")
    public BaseResponse<?> getAllUsersWithSortBy(
            @RequestParam(
                    defaultValue = "0",
                    required = false
            ) int pageNo,
            @Min(10)
            @RequestParam(
                    defaultValue = "20",
                    required = false
            )
            int pageSize
    ) {
        return BaseResponse.builder()
                           .message("User list retrieved successfully")
                           .data(userService.getAllUsersWithSortBy(pageNo, pageSize))
                           .build();
    }

    @Operation(
            summary = "Get list users per pageNo",
            description = "Send a request via this API to get user list by pageNo and pageSize"
    )
    @GetMapping("/list1")
    public BaseResponse<?> getAllUsersWithSortBy1(
            @RequestParam(
                    defaultValue = "0",
                    required = false
            ) int pageNo,
            @Min(10)
            @RequestParam(
                    defaultValue = "20",
                    required = false
            )
            int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return BaseResponse.builder()
                           .message("User list retrieved successfully")
                           .data(userService.getAllUsersWithSortBy(pageNo, pageSize, sortBy))
                           .build();
    }

    @Operation(
            summary = "Get list of users with sort by multiple columns",
            description = "Send a request via this API to get user list by pageNo, pageSize and sort by multiple column"
    )
    @GetMapping("/list-with-sort-by-multiple-columns")
    public BaseResponse<?> getAllUsersWithSortByMultiColumns(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String... sorts
    ) {
        log.info("Request to get user list by pageNo, pageSize and sort by multiple columns");
        return BaseResponse.builder()
                           .status(HttpStatus.OK.value())
                           .message("User list retrieved successfully")
                           .data(userService.getAllUsersWithSortByMultiColumns(
                                   pageNo,
                                   pageSize,
                                   sorts
                           ))
                           .build();
    }

    @Operation(
            summary = "Get list of users and search with paging and sorting by customize query",
            description = "Send a request via this API to get user list by pageNo, pageSize and sort by multiple column"
    )
    @GetMapping("/list-user-and-search-with-paging-and-sorting")
    public BaseResponse<?> getAllUsersWithPagingAndSorting(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy
    ) {
        log.info("Request to get user list by pageNo, pageSize and sort by 1 columns");
        return BaseResponse.builder()
                           .status(HttpStatus.OK.value())
                           .message("User list retrieved successfully")
                           .data(userService.getAllUsersWithPagingAndSorting(
                                   pageNo,
                                   pageSize,
                                   search,
                                   sortBy
                           ))
                           .build();
    }

    @Operation(
            summary = "Advanced search query by criteria",
            description = "Send a request via this API to get user list by pageNo, pageSize and sort by multiple column"
    )
    @GetMapping("/advanced-search-with-criteria")
    public BaseResponse<?> advancedSearchWithCriteria(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String... search
    ) {
        log.info("Request advance search query by criteria");
        return new BaseResponse<>(
                HttpStatus.OK.value(),
                "User list retrieved successfully",
                userService.advancedSearchWithCriteria(
                        pageNo,
                        pageSize,
                        sortBy,
                        address,
                        search
                )
        );
    }

    @Operation(
            summary = "Advanced search query by specification",
            description = "Send a request via this API to get user list by specification"
    )
    @GetMapping(value = "/advanced-search-with-specification", produces = APPLICATION_JSON_VALUE)
    public BaseResponse<?> advancedSearchWithSpecification(
            Pageable pageable,
            @RequestParam(required = false) String[] users,
            @RequestParam(required = false) String[] address
    ) {
        log.info("Request advance search query by specification");
        return new BaseResponse<>(
                HttpStatus.OK.value(),
                "User list retrieved successfully",
                userService.advancedSearchWithSpecification(
                        pageable,
                        users,
                        address
                )
        );
    }
}
