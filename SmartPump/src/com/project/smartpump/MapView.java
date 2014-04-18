package com.project.smartpump;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.classes.GasStation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
public class MapView extends FragmentActivity implements OnMarkerClickListener
{   
    private GoogleMap gMap;
    public static Context context;
    private ArrayList<GasStation> stations;
    private HashMap<Marker, Integer> markerMap;
    private double currentLat;
    private double currentLng;
    boolean markerClicked;
    
    public static Context getContext() 
    {
        return context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        context = getApplicationContext();
        
        gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        gMap.setMyLocationEnabled(true);
        
        //Add markers to map
        stations = this.getIntent().getExtras().getParcelableArrayList("data");
        addMarkersToMap(stations);
        
        //Adjust camera to current location
        currentLat = this.getIntent().getExtras().getDouble("latitude");
        currentLng = this.getIntent().getExtras().getDouble("longitude");
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLng), 10);
        gMap.animateCamera(location);
        
        //Set marker click listener
        gMap.setOnMarkerClickListener(this);
        markerClicked = false;
    }
    
    protected void addMarkersToMap(ArrayList<GasStation> stations)
    {
        markerMap = new HashMap<Marker, Integer>();
        
        int numStations = stations.size();
        for(int i = 0; i < numStations; ++i)
        {
            GasStation s = stations.get(i);
            //Display marker
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(s.getLatitude(), s.getLongitude()))
                    .title(s.getWebAddress())
                    .snippet(String.valueOf(s.getFuelPrice()))
                    .icon(BitmapDescriptorFactory.defaultMarker());
            Marker stationMarker = gMap.addMarker(marker);
            
            //Add to marker map
            this.markerMap.put(stationMarker, i);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) 
    {
        if (markerClicked)
        {
            int chosenIndex = markerMap.get(marker);
            GasStation selected = stations.get(chosenIndex);
            Intent i = new Intent(getContext(), StationDetailsActivity.class);
            i.putExtra("stationSelected", selected);
            startActivity(i);
        }
        else
        {
            markerClicked = true;
        }
        
        return true;
    }
}
