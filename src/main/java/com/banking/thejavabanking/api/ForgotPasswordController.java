package com.banking.thejavabanking.api;

import com.banking.thejavabanking.contract.abstractions.shared.response.BaseResponse;
import com.banking.thejavabanking.domain.authentications.dto.requests.ChangePasswordRequest;
import com.banking.thejavabanking.domain.authentications.services.impl.ForgotPasswordImpl;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot-password")
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {
    ForgotPasswordImpl forgotPassword;

    // send mail
    @GetMapping("/verify-email/{email}")
    public BaseResponse<Void> verifyEmail(
            @PathVariable String email
    ) {
        forgotPassword.verifyEmail(email);
        return BaseResponse.<Void>builder()
                           .message("Email is sent")
                           .build();
    }

    // verify OTP
    @GetMapping("/verify-otp/{otp}/{email}")
    public BaseResponse<Void> verifyOTP(
            @PathVariable Integer otp,
            @PathVariable String email
    ) {
        boolean isVerified = forgotPassword.verifyOTP(email, otp);
        String ans = "OTP is verified";
        if (!isVerified)
            ans = "OTP is not expired time";

        return BaseResponse.<Void>builder()
                           .message(ans)
                           .build();
    }

    @PostMapping("/reset-password/{email}")
    public BaseResponse<Void> resetPassword(
            @RequestBody ChangePasswordRequest changePasswordRequest,
            @PathVariable String email
    ) {
        boolean isChanged = forgotPassword.changePassword(changePasswordRequest, email);
        String ans = "Password is changed";
        if (!isChanged)
            ans = "Password is not changed";
        return BaseResponse.<Void>builder()
                           .message(ans)
                           .build();
    }
}
