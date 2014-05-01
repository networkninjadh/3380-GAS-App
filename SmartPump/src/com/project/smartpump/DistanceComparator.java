package com.project.smartpump;

import java.util.Comparator;

import com.project.classes.StationSearchResult;

public class DistanceComparator implements
		Comparator<StationSearchResult> {

	@Override
	public int compare(StationSearchResult lhs, StationSearchResult rhs) {
		return lhs.getStation().getDistance().compareTo(rhs.getStation().getDistance());
	}

}
