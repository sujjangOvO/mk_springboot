package com.example.moonkey.dto;

import com.example.moonkey.dto.AccountUidDto;
import com.example.moonkey.domain.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StoreDto {

	@NotNull
	private long storeId;

	@NotNull
	private String name;

	@NotNull
	private String address;

	@Column(nullable = true)
	private String description;

	@NotNull
	@JsonIgnore
	private long ownerId;

	public static StoreDto from(Store store){
		return StoreDto.builder()
				.storeId(store.getStoreId())
				.name(store.getName())
				.address(store.getAddress())
				.ownerId(store.getOwnerId().getUid())
				.description(store.getDescription())
				.build();
	}

}
