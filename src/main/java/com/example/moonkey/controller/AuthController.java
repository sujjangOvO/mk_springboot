package com.example.moonkey.controller;


import com.example.moonkey.domain.Account;
import com.example.moonkey.dto.AccountDto;
import com.example.moonkey.dto.LoginDto;
import com.example.moonkey.dto.TokenDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.jwt.JwtFilter;
import com.example.moonkey.jwt.TokenProvider;
import com.example.moonkey.repository.AccountRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/app")
public class AuthController { // 로그인 api
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountRepository accountRepository;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, AccountRepository accountRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/auth/login") // 로그인 api 경로 = /api/authenticate => post 요청을 받음
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPassword());

        Account account = accountRepository.findOneWithAuthoritiesById(loginDto.getId())
                .orElseThrow(() -> new NotFoundMemberException("Member not found"));

        AccountDto accountDto = AccountDto.from(account);


        // authenticate 메소드가 실행될 때 CustomAccountDetailService에서 만든 loadUserByUsername 메소드가 실행된다. 따라서 Authentication 객체를 생성한다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication); // jwt 토큰 생성

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt); // response header에 jwt를 넣어주고

        return new ResponseEntity<>(new TokenDto(jwt, accountDto), httpHeaders, HttpStatus.OK); // TokenDto를 이용해 response body에도 jwt를 넣어 리턴한다.
    }
}