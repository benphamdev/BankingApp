package com.banking.thejavabanking.domain.authentications.services.impl;

import com.banking.thejavabanking.domain.authentications.dto.requests.ChangePasswordRequest;
import com.banking.thejavabanking.domain.authentications.entity.ForgotPassword;
import com.banking.thejavabanking.domain.authentications.repositories.ForgotPasswordRepository;
import com.banking.thejavabanking.domain.authentications.services.IForgotPassword;
import com.banking.thejavabanking.domain.notifications.dto.requests.EmailDetailRequest;
import com.banking.thejavabanking.domain.notifications.services.impl.EmailServiceImpl;
import com.banking.thejavabanking.domain.users.entities.User;
import com.banking.thejavabanking.domain.users.repositories.UserRepository;
import com.banking.thejavabanking.domain.users.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

import static com.banking.thejavabanking.infrastructure.utils.AccountUtils.generateNumber;

@Service
@RequiredArgsConstructor
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
public class ForgotPasswordImpl implements IForgotPassword {
    IUserService userService;
    UserRepository userRepository;
    EmailServiceImpl emailService;
    ForgotPasswordRepository forgotPasswordRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public void verifyEmail(String email) {
        User user = userService.getUserByEmail(email);

        int otp = generateNumber();
        EmailDetailRequest emailDetail = EmailDetailRequest.builder()
                                                           .recipient(email)
                                                           .message("This is your OTP: " + otp + "\n Your OTP will expire in 70 seconds")
                                                           .subject("OTP for forgot password ")
                                                           .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                                                      .otp(otp)
                                                      .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                                                      .user(user)
                                                      .build();

        emailService.sendEmail(emailDetail);
        forgotPasswordRepository.save(forgotPassword);
    }

    @Override
    public boolean verifyOTP(String email, Integer otp) {
        User user = userService.getUserByEmail(email);

        ForgotPassword forgotPassword =
                forgotPasswordRepository.findByOtpAndUser(otp, user)
                                        .orElseThrow(() -> new RuntimeException("OTP not found"));

        int id = forgotPassword.getId();
        if (forgotPassword.getExpirationTime().before(Date.from(java.time.Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return false;
        }
        return true;
    }

    @Override
    public boolean changePassword(ChangePasswordRequest changePasswordRequest, String email) {
        if (!Objects.equals(
                changePasswordRequest.password(),
                changePasswordRequest.confirmPassword()
        ))
            return false;
        String newPassword = passwordEncoder.encode(changePasswordRequest.password());
        userRepository.updatePassword(email, newPassword);
        return true;
    }
}
