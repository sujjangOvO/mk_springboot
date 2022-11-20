package com.example.moonkey.dto;

import com.example.moonkey.domain.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

	@NotNull
	private String name;

	@NotNull
	private String address;

	@Column(nullable = true)
	private String description;

	@NotNull
	@JsonIgnore
	private long ownerId;

	@NotNull
	private String category;

	public static StoreDto from(Store store){
		if(store==null) return null;
		return StoreDto.builder()
				.name(store.getName())
				.address(store.getAddress())
				.ownerId(store.getOwnerId().getUid())
				.description(store.getDescription())
				.category(store.getCategory().getCategoryName())
				.build();
	}

}
