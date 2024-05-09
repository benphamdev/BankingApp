package com.banking.thejavabanking.dto.requests;

import com.banking.thejavabanking.dto.validators.DobConstraint;
import com.banking.thejavabanking.dto.validators.PhoneNumberConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest implements Serializable {
    @NotBlank(message = "FIRST_NAME_REQUIRED")
    String firstName;

    @NotNull(message = "LAST_NAME_REQUIRED")
    String lastName;

    @NotBlank(message = "OTHER_NAME_REQUIRED")
    String otherName;

    @DobConstraint(
            min = 18,
            message = "INVALID_DOB"
    )
    LocalDate dob;

    String gender;

    String address;

    @Email(message = "INVALID_EMAIL")
    String email;

    @PhoneNumberConstraint(
            min = 10,
            message = "INVALID_PHONE_NUMBER"
    )
    String phoneNumber;

    @Size(
            min = 6,
            message = "PASSWORD_INVALID"
    )
    String password;
}
