package com.example.moonkey.service;

import com.example.moonkey.domain.Authority;
import com.example.moonkey.domain.User;
import com.example.moonkey.dto.UserDto;
import com.example.moonkey.exception.DuplicateMemberException;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.repository.UserRepository;
import com.example.moonkey.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다."); // username을 기준으로
        }

        // 겹치는 username이 아니라면 Autority와 User를 생성하여 UserRepository의 save 메소드를 통해 DB에 정보를 저장함.
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER") // 중요한점은 해당 메소드를 통해 가입한 회원은 ROLE_USER라는 권한을 갖고있다. admin 계정은 USER, ADMIN ROLE을 가지고 있다.
                .build();                       // => 이 차이를 통해 권한 검증 부분을 테스트.

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) { // 인자로 받은 username에 해당하는 유저 객체와 권한 정보를 가져오는 메소드
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() { // 현재 Security Context에 저장된 username에 해당하는 유저, 권한 정보를 가져오는 메소드.
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    // 따라서 이 두 가지 메소드의 허용 권한을 다르게 하여 권한 검증에 대한 부분을 테스트한다.
    // UserService의 메소드를 호출할 UserController를 생성
}
