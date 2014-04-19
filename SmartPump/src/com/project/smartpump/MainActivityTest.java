package com.project.smartpump;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.project.classes.FavoritesManager;
import com.project.classes.GasStation;
import com.project.classes.StationRequest;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityTest extends Activity implements LocationListener {
    static EditText address;
    static TextView output, sLat, sLong;
    static Button searchWithAddress, searchWithLocation;
    public static Context context;
    private String provider;
    private double latitude, longitude;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        context = getApplicationContext();
        address = (EditText) findViewById(R.id.address);
        output = (TextView) findViewById(R.id.searchOutput);
        sLat = (TextView) findViewById(R.id.searchLatitude);
        sLong = (TextView) findViewById(R.id.searchLongitude);
        searchWithAddress = (Button) findViewById(R.id.searchWithAddress);
        searchWithLocation = (Button) findViewById(R.id.searchWithLocation);
        searchWithAddress.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) {
                LatLng coords = StationRequest.getGeoCoordsFromAddress(context,
                        address.getText().toString());
                ArrayList<GasStation> stations = StationRequest.NearbyGasStations(coords.latitude, coords.longitude,
                                10.0, "reg");
                Intent i = new Intent(getContext(), MapView.class);
                i.putParcelableArrayListExtra("data", stations);
                i.putExtra("latitude", coords.latitude);
                i.putExtra("longitude", coords.longitude);
                startActivity(i);
            }
        });
        searchWithLocation.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) {
                ArrayList<GasStation> stations = StationRequest
                        .NearbyGasStations(latitude, longitude, 10.0, "reg");
                Intent i = new Intent(getContext(), StationDetailsActivity.class);
                i.putExtra("stationSelected", stations.get(1));
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) 
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            sLat.setText(String.valueOf(latitude));
            sLong.setText(String.valueOf(longitude));
            onLocationChanged(location);
        } 
        else 
        {
            // handle error
        }
    }

    private void testSaveFavorite(ArrayList<GasStation> stations) 
    {
        System.out.println("trying to save favorite");
        FavoritesManager.addFavorite(getContext(), stations.get(0));
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.favorites:
            System.out.println("star clicked");
            Intent i = new Intent(getContext(), FavoritesActivity.class);
            startActivity(i);
            return true;
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        sLat.setText(String.valueOf(latitude));
        sLong.setText(String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }
}
