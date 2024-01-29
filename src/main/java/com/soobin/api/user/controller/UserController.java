package com.soobin.api.user.controller;

import com.soobin.api.authentication.SoobinFilter;
import com.soobin.api.authentication.SoobinToken;
import com.soobin.api.user.dto.req.ReqLogin;
import com.soobin.api.user.dto.req.ReqSignUp;
import com.soobin.api.user.dto.res.ResUser;
import com.soobin.api.user.service.SoobinUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final SoobinUserService soobinUserService;

    @PostMapping("/user/sign-up")
    public ResponseEntity<ResUser> signUp(@RequestBody @Valid ReqSignUp reqSignUp) {
        ResUser resUser = new ResUser(soobinUserService.signUp(reqSignUp.toDto()));
        return new ResponseEntity<>(resUser, HttpStatus.OK);
    }

    @PostMapping("/user/sign-in")
    public ResponseEntity<SoobinToken> signIn(@Valid @RequestBody ReqLogin loginDto) {
        String jwt = soobinUserService.login(loginDto.toDto());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SoobinFilter.AUTHORIZATION_HEADER, SoobinFilter.AUTHORIZATION_HEADER_VALUE + jwt);
        return new ResponseEntity<>(new SoobinToken(jwt), httpHeaders, HttpStatus.OK);
    }
}
