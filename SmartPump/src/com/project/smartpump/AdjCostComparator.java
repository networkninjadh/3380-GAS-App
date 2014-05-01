package com.project.smartpump;

import java.util.Comparator;

import com.project.classes.StationSearchResult;

public class AdjCostComparator implements
		Comparator<StationSearchResult> {

	@Override
	public int compare(StationSearchResult lhs, StationSearchResult rhs) {

		if (lhs.getAdjustedCost() < rhs.getAdjustedCost())
			return -1;
		else if(lhs.getAdjustedCost() > rhs.getAdjustedCost())
			return 1;
		else return 0; 
	}

}
