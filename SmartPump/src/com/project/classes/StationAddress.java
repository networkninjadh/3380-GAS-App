package com.project.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class StationAddress implements Parcelable
{
    private String street;
    private String city;
    private String state;
    private String zip;
    
    public StationAddress(String street, String city, String state, String zip)
    {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    public StationAddress(Parcel in)
    {
        street = in.readString();
        city = in.readString();
        state = in.readString();
        zip = in.readString();
    }
    
    public String getStreet()
    {
        return this.street;
    }
    
    public String getCity()
    {
        return this.city;
    }
    
    public String getState()
    {
        return this.state;
    }
    
    public String getZip()
    {
        return this.zip;
    }

    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getStreet());
        dest.writeString(getCity());
        dest.writeString(getState());
        dest.writeString(getZip());
    }
    
    public static final Parcelable.Creator<StationAddress> CREATOR = new Parcelable.Creator<StationAddress>() 
    {
        public StationAddress createFromParcel(Parcel in) 
        {
            return new StationAddress(in);
        }

        @Override
        public StationAddress[] newArray(int size) 
        {
            return new StationAddress[size];
        }
    };
}