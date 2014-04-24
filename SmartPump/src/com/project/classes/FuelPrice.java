package com.project.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class FuelPrice implements Parcelable{

    private double price;
    private String lastUpdated;
    
    public FuelPrice()
    {
        this.price = 0.0;
        this.lastUpdated = "";
    }
    
    public FuelPrice(double price, String lastUpdated)
    {
        this.price = price;
        this.lastUpdated = lastUpdated;
    }
    public FuelPrice(Parcel in) {
        this.price = in.readDouble();
        this.lastUpdated = in.readString();
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public String getLastUpdated() {
        return this.lastUpdated;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(getPrice());
        dest.writeString(getLastUpdated());
    }
    
    public static final Parcelable.Creator<FuelPrice> CREATOR = new Parcelable.Creator<FuelPrice>() 
    {
        public FuelPrice createFromParcel(Parcel in) 
        {
            return new FuelPrice(in);
        }

        @Override
        public FuelPrice[] newArray(int size) 
        {
            return new FuelPrice[size];
        }
    };
    
}
