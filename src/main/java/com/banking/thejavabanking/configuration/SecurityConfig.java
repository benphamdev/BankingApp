package com.banking.thejavabanking.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/user/create",
            "/auth/**",
            "/transaction/**",
            "/user/**",
            "/province/**",
            "/branch/**",
            "/account/**",
            "/forgot-password/**",
            "/loan-disbursement/**",
            "/loan-detail/**",
            "/push-notification/**",
            "/post/**",
            "/saving/**",
            "/comment/**",
            "/otp/**",
            "/tag/**",
    };

    private final CustomJwtDecoder customJwtDecoder;

    @Autowired
    public SecurityConfig(CustomJwtDecoder customJwtDecoder) {this.customJwtDecoder = customJwtDecoder;}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                    authorize ->
                            authorize.requestMatchers(HttpMethod.POST, AUTH_WHITELIST)
                                     .permitAll()
//                                     .requestMatchers(HttpMethod.GET, AUTH_WHITELIST1)
//                                     .hasAuthority("ROLE_ADMIN")
//                                     .hasRole(Role.ADMIN.name())
                                     .requestMatchers(HttpMethod.GET, AUTH_WHITELIST)
                                     .permitAll()
                                     .requestMatchers(HttpMethod.PUT, AUTH_WHITELIST)
                                     .permitAll()
                                     .requestMatchers(HttpMethod.DELETE, AUTH_WHITELIST)
                                     .permitAll()
                                     .anyRequest()
                                     .authenticated()
            )
            .oauth2Login(Customizer.withDefaults());

        http.oauth2ResourceServer(
                oauth2 ->
                        oauth2.jwt(
                                      jwt ->
                                              jwt.decoder(customJwtDecoder)
                                                 .jwtAuthenticationConverter(
                                                         jwtAuthenticationConverter())
                              )
                              .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        return http.build();
    }

    // convert scope_admin -> role_admin
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
                new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
