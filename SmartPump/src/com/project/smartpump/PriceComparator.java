package com.project.smartpump;

import java.util.Comparator;

import com.project.classes.StationSearchResult;

public class PriceComparator implements
Comparator<StationSearchResult> {

@Override
public int compare(StationSearchResult lhs, StationSearchResult rhs) {

if (lhs.getStation().getSelectedFuelPrice().getPrice() < rhs.getStation().getSelectedFuelPrice().getPrice())
	return -1;
else if(lhs.getStation().getSelectedFuelPrice().getPrice() > rhs.getStation().getSelectedFuelPrice().getPrice())
	return 1;
else return 0; 
}

}
