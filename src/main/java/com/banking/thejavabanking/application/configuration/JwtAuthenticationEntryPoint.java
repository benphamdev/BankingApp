package com.banking.thejavabanking.application.configuration;

import com.banking.thejavabanking.domain.common.constants.EnumsErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        EnumsErrorResponse errorCode = EnumsErrorResponse.UNAUTHENTICATED;
//
//        response.setStatus(errorCode.getHttpStatusCode()
//                                    .value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        BaseResponse<?> apiResponse = BaseResponse.builder()
//                                                  .status(errorCode.getCode())
//                                                  .message(errorCode.getMessage())
//                                                  .build();
        log.error("Unauthenticated error: {}", authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> apiResponse = Map.of(
                "status", errorCode.getCode(),
                "message", authException.getMessage(),
                "error", errorCode.getMessage(),
                "path", request.getServletPath()
        );

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), apiResponse);
//        response.getWriter()
//                .write(new ObjectMapper().writeValueAsString(apiResponse));
//        response.flushBuffer();
    }
}
