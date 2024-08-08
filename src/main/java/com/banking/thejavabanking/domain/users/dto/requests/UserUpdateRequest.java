package com.banking.thejavabanking.domain.users.dto.requests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest implements Serializable {
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    List<String> roles;
}
