package com.banking.thejavabanking.application.mapper;

import com.banking.thejavabanking.domain.users.dto.requests.UserCreationRequest;
import com.banking.thejavabanking.domain.users.dto.requests.UserUpdateRequest;
import com.banking.thejavabanking.domain.users.dto.respones.UserResponse;
import com.banking.thejavabanking.domain.users.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreationRequest request);

    @Mapping(
            source = "photo.url",
            target = "profilePicture"
    )
    UserResponse toResponse(User user);

    @Mapping(
            target = "roles",
            ignore = true
    )
    void updateEntity(
            @MappingTarget User user, UserUpdateRequest request
    );

}
