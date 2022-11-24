package com.example.moonkey.dto;

import com.example.moonkey.domain.Menu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

	@NotNull
	private long menuId;
	@NotNull
	private int price;

	@NotNull
	private String menuName;

	@Column(nullable=true)
	private String options;

	@Column(nullable = true)
	private String description;

	@NotNull
	@JsonIgnore
	private long storeId;

	public static MenuDto from(Menu menu){
		return MenuDto.builder()
				.menuId(menu.getMenuId())
				.menuName(menu.getMenuName())
				.price(menu.getPrice())
				.options(menu.getOptions())
				.description(menu.getDescription())
				.storeId(menu.getStoreId().getStoreId())
				.build();
	}
}
