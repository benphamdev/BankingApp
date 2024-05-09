package com.banking.thejavabanking.mapper;

import com.banking.thejavabanking.dto.requests.UserCreationRequest;
import com.banking.thejavabanking.dto.requests.UserUpdateRequest;
import com.banking.thejavabanking.dto.respones.UserResponse;
import com.banking.thejavabanking.models.entity.User;
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
