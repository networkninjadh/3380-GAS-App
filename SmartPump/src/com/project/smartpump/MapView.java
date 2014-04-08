package com.project.smartpump;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
public class MapView extends FragmentActivity
{	private GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {	super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        gMap.setMyLocationEnabled(true);
    }
}
