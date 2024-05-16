package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.LoginRequestDTO;
import com.banking.thejavabanking.dto.requests.LogoutRequest;
import com.banking.thejavabanking.dto.requests.RefreshTokenRequest;
import com.banking.thejavabanking.dto.respones.AuthenticationResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse login(LoginRequestDTO loginRequestDTO);

    void logout(LogoutRequest token) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(
            RefreshTokenRequest token
    ) throws ParseException, JOSEException;

}
