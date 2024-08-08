package com.banking.thejavabanking.domain.users.dto.respones;

import com.banking.thejavabanking.domain.authentications.dto.responses.RoleResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    long id;
    String firstName;
    String lastName;
    String otherName;
    LocalDate dob;
    String gender;
    String address;
    String email;
    String phoneNumber;
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
