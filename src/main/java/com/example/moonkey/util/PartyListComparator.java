package com.example.moonkey.util;

import com.example.moonkey.dto.StatsDto;

import java.util.Comparator;

public class PartyListComparator implements Comparator<StatsDto> {
	@Override
	public int compare(StatsDto x, StatsDto y){
		return compare(x.getCounts(),y.getCounts());
	}

	private int compare (int a, int b){
		return Integer.compare(a, b);
	}
}
