package com.example.moonkey.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StatsDto {

	@NotNull
	private String category;

	@NotNull
	private float score;

	private int counts;
}
