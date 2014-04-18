/**
 * @author Damond Howard
 */
package com.project.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class GasStation implements Parcelable {
    private double fuelPrice;
    private String phoneNumber;
    private String webAddress;
    private String city;
    private String state;
    private int zipCode;
    private double latitude;
    private double longitude;

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
    GasStation(double fuelPrice, String phoneNumber, String webAddress,
            String city, String state, int zipCode, double lat, double lng) {
        this.fuelPrice = fuelPrice;
        this.phoneNumber = phoneNumber;
        this.webAddress = webAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.latitude = lat;
        this.longitude = lng;
    }
    
    public GasStation(Parcel in) {
        webAddress = in.readString();
        city = in.readString();
        state = in.readString();
        zipCode = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        fuelPrice = in.readDouble();
        phoneNumber = in.readString();
    }


    /**
     * 
     * @return the fuel price of this station
     */
    public double getFuelPrice() {
        return this.fuelPrice;
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
    public int getZipCode() {
        return this.zipCode;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
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
        dest.writeDouble(getFuelPrice());
        dest.writeString(getPhoneNumber());
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

        result.append("Address: " + getWebAddress() + nl);
        result.append("City: " + getCity() + nl);
        result.append("State: " + getState() + nl);
        result.append("Zip: " + getZipCode() + nl);
        result.append("Latitude: " + getLatitude() + nl);
        result.append("Longitude: " + getLongitude() + nl);
        result.append("Phone Number: " + getPhoneNumber() + nl);
        result.append("Fuel Price: " + getFuelPrice() + nl);
        result.append(nl);
        return result.toString();
    }
}