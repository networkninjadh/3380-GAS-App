package com.project.smartpump;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.classes.GasStation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StationDetailsActivity extends Activity 
{
    public static Context context;
    private GoogleMap gMap;
    private static TextView name, address, cityStateZip, phone, price, adjustedPrice, distance;
    private double currentLat, currentLng, stationLat, stationLng;

    public static Context getContext() 
    {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_station_view);
        context = getApplicationContext();
        
        gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.miniMap)).getMap();
        gMap.setMyLocationEnabled(true);
        
        name = (TextView)findViewById(R.id.stationName);
        address = (TextView)findViewById(R.id.stationAddress);
        cityStateZip = (TextView)findViewById(R.id.cityStateZip);
        phone = (TextView)findViewById(R.id.stationPhone);
        price = (TextView)findViewById(R.id.stationPrice);
        adjustedPrice = (TextView)findViewById(R.id.stationAdjustedCost);
        distance = (TextView)findViewById(R.id.distanceAway);
        
        GasStation station = this.getIntent().getExtras().getParcelable("stationSelected");
        
        //Get current location
        currentLat = this.getIntent().getExtras().getDouble("latitude");
        currentLng = this.getIntent().getExtras().getDouble("longitude");
        
        //Add marker to mini map
        stationLat = station.getLatitude();
        stationLng = station.getLongitude();
        MarkerOptions marker = new MarkerOptions()
            .position(new LatLng(stationLat, stationLng))
            .title(station.getWebAddress())
            .snippet(String.valueOf(station.getFuelPrice()))
            .icon(BitmapDescriptorFactory.defaultMarker());
        gMap.addMarker(marker);
       
        //Adjust camera to marker location
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(stationLat, stationLng), 10);
        gMap.animateCamera(location);
        
        //Add station details
        name.setText(station.getStationName());
        address.setText(station.getWebAddress());
        cityStateZip.setText(station.getCity() + ", " + station.getState() + " " + station.getZipCode());
        phone.setText(station.getPhoneNumber());
        price.setText(String.valueOf(station.getFuelPrice()));
        adjustedPrice.setText("0");
        distance.setText(String.valueOf(station.getDistance()));
    }
}
