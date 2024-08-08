package com.banking.thejavabanking.domain.authentications.services;

import com.banking.thejavabanking.domain.authentications.dto.requests.ChangePasswordRequest;

public interface IForgotPassword {
    void verifyEmail(String email);

    boolean verifyOTP(String email, Integer otp);

    boolean changePassword(ChangePasswordRequest changePasswordRequest, String email);
}
