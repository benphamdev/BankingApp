package com.banking.thejavabanking.domain.authentications.services;

import com.banking.thejavabanking.domain.authentications.dto.requests.LoginRequestDTO;
import com.banking.thejavabanking.domain.authentications.dto.requests.LogoutRequest;
import com.banking.thejavabanking.domain.authentications.dto.requests.RefreshTokenRequest;
import com.banking.thejavabanking.domain.authentications.dto.responses.AuthenticationResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse login(LoginRequestDTO loginRequestDTO);

    void logout(LogoutRequest token) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(
            RefreshTokenRequest token
    ) throws ParseException, JOSEException;

}
