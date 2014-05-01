package com.project.classes;

import com.google.android.gms.maps.model.LatLng;

import android.os.Parcel;
import android.os.Parcelable;

public class GasStation implements Parcelable {
    
    private String stationId;
    private String stationName;
    private String phoneNumber;
    private LatLng coords;
    private double distance; //in Miles
    private StationAddress stationAddress;
    private FuelPrice selectedFuelPrice;
    private FuelPrice regPrice;
    private FuelPrice midPrice;
    private FuelPrice prePrice;
    private FuelPrice dieselPrice;

    GasStation(String stationId, String stationName, String phoneNumber, LatLng coords, StationAddress address, 
            FuelPrice selectedPrice, double distance)
    {
        this.stationId = stationId;
        this.stationName = stationName;
        this.phoneNumber = phoneNumber;
        this.coords = coords;
        this.stationAddress = address;
        this.selectedFuelPrice = selectedPrice;
        this.distance = distance;
    }
    
    GasStation(String stationId, String stationName, String phoneNumber, LatLng coords, StationAddress address, 
            FuelPrice reg, FuelPrice mid, FuelPrice pre, FuelPrice diesel)
    {
        this.stationId = stationId;
        this.stationName = stationName;
        this.phoneNumber = phoneNumber;
        this.coords = coords;
        this.stationAddress = address;
        this.regPrice = reg;
        this.midPrice = mid;
        this.prePrice = pre;
        this.dieselPrice = diesel;
    }
            
    public GasStation(Parcel in) {
        stationId = in.readString();
        stationName = in.readString();
        phoneNumber = in.readString();
        distance = in.readDouble();
        
        coords = in.readParcelable(LatLng.class.getClassLoader());
        stationAddress = in.readParcelable(StationAddress.class.getClassLoader());
        selectedFuelPrice = in.readParcelable(FuelPrice.class.getClassLoader());
        regPrice = in.readParcelable(FuelPrice.class.getClassLoader());
        midPrice = in.readParcelable(FuelPrice.class.getClassLoader());
        prePrice = in.readParcelable(FuelPrice.class.getClassLoader());
        dieselPrice = in.readParcelable(FuelPrice.class.getClassLoader());
    }

    public String getStationId()
    {
        return this.stationId;
    }
    
    public String getStationName()
    {
        return this.stationName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public LatLng getCoords()
    {
        return this.coords;
    }
    
    public StationAddress getStationAddress()
    {
        return this.stationAddress;
    }
    
    public void setDistance(double value)
    {
        this.distance = value;
    }
    
    public Double getDistance()
    {
        return this.distance;
    }
    
    public FuelPrice getSelectedFuelPrice()
    {
        return this.selectedFuelPrice;
    }
    
    public FuelPrice getRegPrice()
    {
        return this.regPrice;
    }   
    
    public FuelPrice getMidPrice()
    {
        return this.midPrice;
    }
    
    public FuelPrice getPrePrice()
    {
        return this.prePrice;
    }
    
    public FuelPrice getDieselPrice()
    {
        return this.dieselPrice;
    }
    
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getStationId());
        dest.writeString(getStationName());
        dest.writeString(getPhoneNumber());
        dest.writeDouble(getDistance());
        
        dest.writeParcelable(getCoords(), flags);
        dest.writeParcelable(getStationAddress(), flags);
        dest.writeParcelable(getSelectedFuelPrice(), flags);
        dest.writeParcelable(getRegPrice(), flags);
        dest.writeParcelable(getMidPrice(), flags);
        dest.writeParcelable(getPrePrice(), flags);
        dest.writeParcelable(getDieselPrice(),  flags);
    }

    public static final Parcelable.Creator<GasStation> CREATOR = new Parcelable.Creator<GasStation>() 
    {
        public GasStation createFromParcel(Parcel in) 
        {
            return new GasStation(in);
        }

        @Override
        public GasStation[] newArray(int size) 
        {
            return new GasStation[size];
        }
    };
    
    @Override
    public String toString() 
    {
        StringBuilder result = new StringBuilder();
        String nl = System.getProperty("line.separator");

        result.append("Name: " + getStationName() + nl);
        result.append("Phone Number: " + getPhoneNumber() + nl);
        result.append("Station Id:" + getStationId() + nl);
        result.append(nl);
        return result.toString();
    }
}