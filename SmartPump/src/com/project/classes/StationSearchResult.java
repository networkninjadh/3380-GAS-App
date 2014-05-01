package com.project.classes;

import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;

public class StationSearchResult implements Parcelable, Comparable<StationSearchResult>
{
    private GasStation station;
    private double adjustedCost;
    
    public StationSearchResult(GasStation station, double adjustedCost)
    {
        this.station = station;
        this.adjustedCost = adjustedCost;
    }
    
    public StationSearchResult(Parcel in)
    {
        adjustedCost = in.readDouble();
        station = in.readParcelable(GasStation.class.getClassLoader());
    }

    public GasStation getStation()
    {
        return this.station;
    }
    
    public double getAdjustedCost()
    {
        return this.adjustedCost;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(getAdjustedCost());
        dest.writeParcelable(getStation(), flags);
    }
    
    public static final Parcelable.Creator<StationSearchResult> CREATOR = new Parcelable.Creator<StationSearchResult>() 
    {
        public StationSearchResult createFromParcel(Parcel in) 
        {
            return new StationSearchResult(in);
        }

        @Override
        public StationSearchResult[] newArray(int size) 
        {
            return new StationSearchResult[size];
        }
    };
    ////////////////////////////methods to implement for sort////////////////////////////////////////
	@Override
	public int compareTo(StationSearchResult that) //compareTo sorts according to adjusted cost
	{
		final int EQUAL = 0;
		final int BEFORE = -1;
		final int AFTER = 1;

		if (this.adjustedCost < that.adjustedCost) 
			return BEFORE;
		else if (this.adjustedCost > that.adjustedCost)
			return AFTER;
		else return EQUAL;
	}
	
	public static Comparator<StationSearchResult> StationSearchResultComparator 
    = new Comparator<StationSearchResult>() 
    {
		@Override
		public int compare(StationSearchResult station1, StationSearchResult station2) 
		{
			String stationName1 = station1.station.getStationName().toUpperCase();
			String stationName2 = station2.station.getStationName().toUpperCase();
			return stationName1.compareTo(stationName2);
		}
		
    };

}
