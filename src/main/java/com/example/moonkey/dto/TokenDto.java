package com.example.moonkey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto { // token 정보 response
    private String token;
    private AccountDto accountDto;
}
