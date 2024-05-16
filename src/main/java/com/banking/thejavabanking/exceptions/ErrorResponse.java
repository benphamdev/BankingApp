package com.banking.thejavabanking.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public enum ErrorResponse {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_EXISTS(1002, "User already exists", HttpStatus.BAD_REQUEST),
    //    USERNAME_INVALID(
//            1003,"Username must be at least {min} characters long",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(
            1004,
            "Password must be at least {min} characters long",
            HttpStatus.BAD_REQUEST
    ),
    USER_NOT_FOUND(1005, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Access denied", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(1009, "Email already exists", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTS(1010, "Phone number already exists", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE(1011, "Insufficient balance", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND(1012, "Account not found", HttpStatus.NOT_FOUND),
    PROVINCE_NOT_FOUND(1013, "Province not found", HttpStatus.NOT_FOUND),
    PROVINCE_EXISTS(1014, "Province already exists", HttpStatus.BAD_REQUEST),
    BRANCH_NOT_FOUND(1015, "Branch not found", HttpStatus.NOT_FOUND),
    INVALID_INPUT(1016, "Invalid input", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(
            1017,
            "Invalid phone number must be length is : {min}",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_IMAGE(1018, "Invalid image", HttpStatus.BAD_REQUEST),
    FIRST_NAME_REQUIRED(1019, "First name is required", HttpStatus.BAD_REQUEST),
    LAST_NAME_REQUIRED(1020, "Last name is required", HttpStatus.BAD_REQUEST),
    OTHER_NAME_REQUIRED(1021, "Other name is required", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1022, "Invalid email", HttpStatus.BAD_REQUEST),
    ACCOUNT_EXISTS(1023, "Account already exists", HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT_NUMBER(
            1024,
            "Invalid account number must be length is : {min}",
            HttpStatus.BAD_REQUEST
    ),
    LOAN_DETAIL_EXISTS(
            1025,
            "Loan detail already exists. Every user have 1 loan. Sorry for this inconvenience",
            HttpStatus.BAD_REQUEST
    ),
    POST_NOT_FOUND(1026, "Post not found", HttpStatus.NOT_FOUND),
    TAG_NOT_FOUND(1027, "Tag not found", HttpStatus.NOT_FOUND),
    TAG_ALREADY_EXISTS(1028, "Tag already exists", HttpStatus.BAD_REQUEST),
    SAVING_NOT_FOUND(1029, "Saving not found", HttpStatus.NOT_FOUND),
    ;

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
