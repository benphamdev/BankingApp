package com.banking.thejavabanking.dto.respones;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    int id;
    String firstName;
    String lastName;
    String otherName;
    String email;
    String profilePicture;
    Set<RoleResponse> roles;

    public UserResponse(
            int id, String username, String firstName, String lastName, String otherName,
            String email, String profilePicture
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public UserResponse(
            int id, String username, String firstName, String lastName, String otherName,
            String email
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.email = email;
    }
}
