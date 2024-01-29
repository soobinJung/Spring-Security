package com.soobin.api.user.service;

import com.soobin.api.authentication.SoobinTokenProvider;
import com.soobin.api.user.domain.SoobinUser;
import com.soobin.api.user.dto.SoobinUserDto;
import com.soobin.api.user.repository.SoobinUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoobinUserService {

    private final PasswordEncoder passwordEncoder;
    private final SoobinTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SoobinUserRepository soobinUserRepository;

    /**
     * 회원가입
     */
    @Transactional
    public SoobinUserDto signUp(SoobinUserDto szsUserDto){

        checkDuplicateUserId(szsUserDto.getEmail());
        SoobinUser user = szsUserDto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        soobinUserRepository.save(user);

        return user.toDto();
    }

    /**
     * 로그인 성공시 JWT 반환
     */
    public String login(SoobinUserDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  tokenProvider.createToken(authentication);
    }


    /**
     * 아이디 중복 체크
     */
    private void checkDuplicateUserId(String email){
        Optional.ofNullable(soobinUserRepository.findByEmail(email)).ifPresent(szsUser -> {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        });
    }
}
