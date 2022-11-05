package com.example.moonkey.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void passwordEncoder(){
        //given
        String pw="12345678";

        // when
        String encodedPassword = passwordEncoder.encode(pw);

        // then
        assertAll(
                ()-> assertNotEquals(pw, encodedPassword),
                ()-> assertTrue(passwordEncoder.matches(pw,encodedPassword))
        );
    }
}
