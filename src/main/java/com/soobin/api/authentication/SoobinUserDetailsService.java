package com.soobin.api.authentication;

import com.soobin.api.user.domain.SoobinUser;
import com.soobin.api.user.repository.SoobinUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SoobinUserDetailsService implements UserDetailsService {

    private final SoobinUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
    }

    private User createUser(SoobinUser user) {
        return new User(user.getEmail(),user.getPassword(), AuthorityUtils.NO_AUTHORITIES);
    }
}
