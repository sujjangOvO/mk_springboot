package com.example.moonkey.dto;

import com.example.moonkey.domain.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto { // 회원가입시 사용할 Dto


    @NotNull
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 필드값을 쓰는 경우에만 접근이 허용
    @NotNull
    private String key;

    @NotNull
    private String phone;

    @NotNull
    private String nickname;

    @NotNull
    private int flag;

    private Set<AuthorityDto> authorityDtoSet;

    public static AccountDto from(Account account){
        if(account == null) return null;

        return AccountDto.builder()
                .id(account.getId())
                .key(account.getKey())
                .phone(account.getPhone())
                .flag(account.getFlag())
                .authorityDtoSet(account.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

}