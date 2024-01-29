package com.soobin.api.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SoobinSecurityConfig {

    private final SoobinTokenProvider tokenProvider;
    private final SoobinAuthenticationFailureHandler authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // CSRF 설정 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // 예외 처리
                .exceptionHandling(customizer -> {customizer.authenticationEntryPoint(authenticationEntryPoint);})

                // 세션 관리 STATELESS 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 인증 요청 처리
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/user/login").permitAll()
                                .requestMatchers("/user/sign-up").permitAll()
                                .requestMatchers("/user/sign-in").permitAll()
                                .anyRequest().authenticated()
                )

                .addFilterBefore(new SoobinFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
