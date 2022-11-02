package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.repository.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component("accountDetailsService")
public class CustomAccountDetailService implements UserDetailsService { // UserDetailsService는 Spring Security에서 사용자의 정보를 담는 인터페이스이다.
                                                    // Spring Security에서 사용자의 정보를 불러오기 위해 구현하는 인터페이스이다.

    private final AccountRepository accountRepository;

    public CustomAccountDetailService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // 로그인시에 db에서 유저정보와 권한정보를 가져온다.
    // 해당 정보를 기반으로 userdetails.User 객체를 생성
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username){
        return accountRepository.findOneWithAuthoritiesById(username)
                .map(account -> createUser(username, account))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }



    private org.springframework.security.core.userdetails.User createUser(String username, Account account){

        if (!account.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = account.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(account.getId(),
                account.getPassword(),
                grantedAuthorities);
    }


}
