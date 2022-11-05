package com.example.moonkey.dto;

import com.example.moonkey.domain.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Builder
@AllArgsConstructor
public class AccountUidDto {

	@NotNull
	private long uid;

	public static AccountUidDto from(Account account){
		if(account == null) return null;

		return AccountUidDto.builder()
				.uid(account.getUid())
				.build();
	}
}
