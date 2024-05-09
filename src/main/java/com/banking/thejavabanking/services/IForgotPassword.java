package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.ChangePasswordRequest;

public interface IForgotPassword {
    void verifyEmail(String email);

    boolean verifyOTP(String email, Integer otp);

    boolean changePassword(ChangePasswordRequest changePasswordRequest, String email);
}
