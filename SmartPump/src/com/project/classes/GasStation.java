/**
 * @author Damond Howard
 */
package com.project.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class GasStation implements Parcelable {
    private double fuelPrice;
    private FuelPrice regPrice;
    private FuelPrice midPrice;
    private FuelPrice prePrice;
    private FuelPrice dieselPrice;
    private String stationName;
    private String phoneNumber;
    private String webAddress;
    private String city;
    private String state;
    private int zipCode;
    private double latitude;
    private double longitude;
    private double distance; //in Miles
    private String stationId;

    /**
     * creates a gas station object which represents a particular gas station
     * 
     * @param fuelPrice
     * @param phoneNumber
     * @param webAddress
     * @param city
     * @param state
     * @param zipCode
     */
    GasStation(double fuelPrice, String stationName, String phoneNumber, String webAddress,
            String city, String state, int zipCode, double lat, double lng, 
            double distance, String stationId) 
    {
        this.fuelPrice = fuelPrice;
        this.stationName = stationName;
        this.phoneNumber = phoneNumber;
        this.webAddress = webAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.latitude = lat;
        this.longitude = lng;
        this.distance = distance;
        this.stationId = stationId;
    }
    
    GasStation(String stationName, String phoneNumber, String webAddress,
            String city, String state, int zipCode, double lat, double lng, 
            double distance, String stationId, FuelPrice reg, FuelPrice mid,
            FuelPrice pre, FuelPrice diesel)
    {
        this.stationName = stationName;
        this.phoneNumber = phoneNumber;
        this.webAddress = webAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.latitude = lat;
        this.longitude = lng;
        this.distance = distance;
        this.stationId = stationId;
        this.regPrice = reg;
        this.midPrice = mid;
        this.prePrice = pre;
        this.dieselPrice = diesel;
        this.fuelPrice = 0.0;
    }
            
    public GasStation(Parcel in) {
        webAddress = in.readString();
        city = in.readString();
        state = in.readString();
        zipCode = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        distance = in.readDouble();
        fuelPrice = in.readDouble();
        phoneNumber = in.readString();
        stationId = in.readString();
        stationName = in.readString();
        midPrice = in.readParcelable(FuelPrice.class.getClassLoader());
    }


    /**
     * 
     * @return the fuel price of this station
     */
    public double getFuelPrice() {
        return this.fuelPrice;
    }

    public String getStationName()
    {
        return this.stationName;
    }
    /**
     * 
     * @return the phone number of the gas station
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * 
     * @return the web site of this gas station
     */
    public String getWebAddress() {
        return this.webAddress;
    }

    /**
     * 
     * @return the city for this gas station
     */
    public String getCity() {
        return this.city;
    }

    /**
     * 
     * @return the state of the gas station
     */
    public String getState() {
        return this.state;
    }

    /**
     * 
     * @return the zip code of the gas station
     */
    public int getZipCode() 
    {
        return this.zipCode;
    }

    public double getLatitude() 
    {
        return this.latitude;
    }

    public double getLongitude() 
    {
        return this.longitude;
    }

    public String getStationId()
    {
    	return this.stationId;
    }
    
    public void setDistance(double value)
    {
        this.distance = value;
    }
    
    public Double getDistance()
    {
        return this.distance;
    }
    
    public FuelPrice getMidPrice()
    {
        return this.midPrice;
    }
    
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getWebAddress());
        dest.writeString(getCity());
        dest.writeString(getState());
        dest.writeInt(getZipCode());
        dest.writeDouble(getLatitude());
        dest.writeDouble(getLongitude());
        dest.writeDouble(getDistance());
        dest.writeDouble(getFuelPrice());
        dest.writeString(getPhoneNumber());
        dest.writeString(getStationId());
        dest.writeString(getStationName());
        dest.writeParcelable(getMidPrice(), flags);
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
            // TODO Auto-generated method stub
            return new GasStation[size];
        }
    };
    
    @Override
    public String toString() 
    {
        StringBuilder result = new StringBuilder();
        String nl = System.getProperty("line.separator");

        result.append("Name: " + getStationName() + nl);
        result.append("Address: " + getWebAddress() + nl);
        result.append("City: " + getCity() + nl);
        result.append("State: " + getState() + nl);
        result.append("Zip: " + getZipCode() + nl);
        result.append("Latitude: " + getLatitude() + nl);
        result.append("Longitude: " + getLongitude() + nl);
        result.append("Phone Number: " + getPhoneNumber() + nl);
        result.append("Fuel Price: " + getFuelPrice() + nl);
        result.append("Station Id:" + getStationId() + nl);
        result.append(nl);
        return result.toString();
    }
}