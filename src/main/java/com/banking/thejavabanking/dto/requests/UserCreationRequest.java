package com.banking.thejavabanking.dto.requests;

import com.banking.thejavabanking.dto.validators.DobConstraint;
import com.banking.thejavabanking.dto.validators.PhoneNumberConstraint;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest implements Serializable {
    @NotBlank(message = "FIRST_NAME_REQUIRED")
    String firstName;

    @NotNull(message = "LAST_NAME_REQUIRED")
    String lastName;

    @DobConstraint(
            min = 18,
            message = "INVALID_DOB"
    )
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    @JsonFormat(pattern = "MM/dd/yyyy")
    LocalDate dob;

    String gender;

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

    @NotEmpty(message = "ADDRESS_REQUIRED")
    Set<AddressRequestDTO> addresses;

    public UserCreationRequest(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
