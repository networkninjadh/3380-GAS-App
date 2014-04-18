package com.project.smartpump;

import java.util.ArrayList;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.classes.GasStation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
public class MapView extends FragmentActivity
{   
    private GoogleMap gMap;
    private double currentLat;
    private double currentLng;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        gMap.setMyLocationEnabled(true);
        
        //Add markers to map
        ArrayList<GasStation> stations = this.getIntent().getExtras().getParcelableArrayList("data");
        addMarkersToMap(stations);
        
        //Adjust camera to current location
        currentLat = this.getIntent().getExtras().getDouble("latitude");
        currentLng = this.getIntent().getExtras().getDouble("longitude");
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLng), 10);
        gMap.animateCamera(location);
    }
    
    protected void addMarkersToMap(ArrayList<GasStation> stations)
    {
        for(GasStation s: stations)
        {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(s.getLatitude(), s.getLongitude())).title(String.valueOf(s.getFuelPrice()));
            marker.icon(BitmapDescriptorFactory.defaultMarker());
            gMap.addMarker(marker);
        }
    }
}
