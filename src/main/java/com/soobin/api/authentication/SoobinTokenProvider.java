package com.soobin.api.authentication;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.security.SecurityException ;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.security.Key;
import java.util.Base64;
import java.util.stream.Collectors;

import io.jsonwebtoken.security.Keys;

@Component
public class SoobinTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private final long tokenValidityInMilliseconds;
    private final Key key;

    public SoobinTokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    /**
     * 토큰 생성
     */
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    /**
     * Token에 담겨있는 정보를 이용해 Authentication 객체를 리턴
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        User principal = new User(claims.getSubject(), "", Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(principal, token, Collections.emptyList());
    }

    /**
     * 토큰의 유효성
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (
                SecurityException
                        | MalformedJwtException
                        | ExpiredJwtException
                        | UnsupportedJwtException
                        | IllegalArgumentException e) {
            return false;
        }
    }
}
