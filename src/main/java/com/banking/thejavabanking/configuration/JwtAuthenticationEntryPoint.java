package com.banking.thejavabanking.configuration;

import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        ErrorResponse errorCode = ErrorResponse.UNAUTHORIZED;

        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        BaseResponse<?> apiResponse = BaseResponse.builder()
                                                  .status(errorCode.getCode())
                                                  .message(errorCode.getMessage())
                                                  .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
