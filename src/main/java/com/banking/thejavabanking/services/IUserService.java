package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.UserCreationRequest;
import com.banking.thejavabanking.dto.requests.UserUpdateRequest;
import com.banking.thejavabanking.dto.respones.PageResponse;
import com.banking.thejavabanking.dto.respones.UserResponse;
import com.banking.thejavabanking.models.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {
    UserResponse createUser(UserCreationRequest userRequest);

    User getUserByEmail(String email);

    User getUserByAccountNumber(String accountNumber);

    UserResponse getUserReponeseByID(Integer id);

    UserResponse getMyProfile();

    UserResponse updateUser(Integer id, UserUpdateRequest userUpdateRequest);

    UserResponse updateProfile(int userId, MultipartFile profilePicture) throws IOException;

    void deleteUser(Integer id);

    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize);

    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersWithSortByMultiColumns(
            int pageNo, int pageSize, String... sorts
    );

    PageResponse<?> getAllUsersWithPagingAndSorting(
            int pageNo, int pageSize, String search, String sortBy
    );

    PageResponse<?> advancedSearchWithCriteria(
            int pageNo, int pageSize, String sortBy, String address, String... search
    );

    PageResponse<?> advancedSearchWithSpecification(
            Pageable pageable, String[] users, String[] search
    );
}
