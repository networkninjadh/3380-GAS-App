package com.project.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class StationSearchResult implements Parcelable
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

}
