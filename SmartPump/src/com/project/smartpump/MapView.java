package com.project.smartpump;

import java.util.ArrayList;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.classes.GasStation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
public class MapView extends FragmentActivity
{	private GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {	System.out.println("map here1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        gMap.setMyLocationEnabled(true);
        System.out.println("here2");
        ArrayList<GasStation> stations = this.getIntent().getExtras().getParcelableArrayList("data");
        addMarkersToMap(stations);
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
