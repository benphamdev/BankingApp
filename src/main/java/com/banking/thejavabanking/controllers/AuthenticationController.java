package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.IntroSpectRequest;
import com.banking.thejavabanking.dto.requests.LoginRequestDTO;
import com.banking.thejavabanking.dto.requests.LogoutRequest;
import com.banking.thejavabanking.dto.requests.RefreshTokenRequest;
import com.banking.thejavabanking.dto.respones.AuthenticationResponse;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.dto.respones.IntrospectResponse;
import com.banking.thejavabanking.services.impl.AuthenticationImpl;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(
        makeFinal = true,
        level = lombok.AccessLevel.PRIVATE
)
public class AuthenticationController {
    AuthenticationImpl authenticationService;

    @Operation(
            summary = "Login",
            description = "Login to the system"
    )
    @PostMapping("/login")
    BaseResponse<AuthenticationResponse> authenticate(
            @RequestBody @Valid LoginRequestDTO request
    ) {
        var res = authenticationService.login(request);
        return BaseResponse.<AuthenticationResponse>builder()
                           .message("Login success")
                           .data(res)
                           .build();
    }

    @PostMapping("/introspect")
    BaseResponse<IntrospectResponse> authenticate(
            @RequestBody @Valid IntroSpectRequest request
    ) throws ParseException, JOSEException {
        var introspectResponse = authenticationService.introspect(request);
        return BaseResponse.<IntrospectResponse>builder()
                           .data(introspectResponse)
                           .build();
    }

    @PostMapping("/logout")
    BaseResponse<Void> logout(
            @RequestBody @Valid LogoutRequest logoutRequest
    ) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return BaseResponse.<Void>builder()
                           .message("Logout success")
                           .build();
    }

    @PostMapping("/refresh")
    BaseResponse<AuthenticationResponse> refreshToken(
            @RequestBody @Valid RefreshTokenRequest refreshToken
    ) throws ParseException, JOSEException {
        var res = authenticationService.refreshToken(refreshToken);
        return BaseResponse.<AuthenticationResponse>builder()
                           .data(res)
                           .build();
    }
}
