package com.banking.thejavabanking.application.configuration;

import com.banking.thejavabanking.domain.authentications.dto.requests.IntroSpectRequest;
import com.banking.thejavabanking.domain.authentications.services.impl.AuthenticationImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private final AuthenticationImpl authenticationService;
    @Value("${jwt.secret}")
    private String signatureKey;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    public CustomJwtDecoder(AuthenticationImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        var response = authenticationService.introspect(
                IntroSpectRequest
                        .builder()
                        .token(token)
                        .build());

        if (!response.isValid())
            throw new JwtException("Token invalid");

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signatureKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                                               .macAlgorithm(MacAlgorithm.HS512)
                                               .build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}
