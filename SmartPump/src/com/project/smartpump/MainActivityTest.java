package com.project.smartpump;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.project.classes.Calculations;
import com.project.classes.FavoritesManager;
import com.project.classes.GasStation;
import com.project.classes.PreferencesHelper;
import com.project.classes.StationRequest;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityTest extends Activity implements LocationListener {
    public static Context context;
    static EditText address;
    static Button searchWithAddress, searchWithLocation;
    static Spinner fuelType;
    private String provider;
    private double latitude, longitude;
    private double MPG;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        context = getApplicationContext();
        
        // Verify that there is saved vehicle data
        PreferencesHelper prefs = new PreferencesHelper(context);
        
        //For now hard-coding a vehicle
        MPG = 0.0;
        String vId = prefs.GetPreferences("VehicleID");
        String mpg = vId.equals("") ? "" : prefs.GetPreferences("VehicleMPG");
        if (mpg.equals("")) 
        {
            //Prompt user to save a vehicle profile
            profileErrorAlert();
        } 
        else 
        {
            MPG = Double.parseDouble(mpg);
        }

        // Set up spinner menu for selecting fuel type
        fuelType = (Spinner) findViewById(R.id.fuelType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.fuel_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelType.setAdapter(adapter);
        fuelType.setSelection(0);

        // Functionality for handling searches
        address = (EditText) findViewById(R.id.address);
        searchWithAddress = (Button) findViewById(R.id.searchWithAddress);
        searchWithLocation = (Button) findViewById(R.id.searchWithLocation);
        searchWithAddress.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                LatLng coords = StationRequest.getGeoCoordsFromAddress(context,
                        address.getText().toString());
                if (coords == null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Could not find location")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() 
                        {   @Override
                            public void onClick(DialogInterface dialog, int which) 
                            { 
                                address.setText("");
                                dialog.dismiss();
                            }
                        });
                }
                else
                {
                    getResults(coords.latitude, coords.longitude);
                }
            }
        });
        searchWithLocation.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                getResults(latitude, longitude);
            }
        });

        // Set up location manager for getting GPS coordinates
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // If GPS is not enabled, take user to screen to enable it
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) 
        {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        // Get last known location
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) 
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            onLocationChanged(location);
        } 
        else 
        {
            // What to do if last known collection cannot be found
            // Will not be able to do routing if requested
        }
    }

    private void profileErrorAlert()
    {
        String message = "It seems that you do not hava a complete vehicle profile. Adjusted pump prices" +
                " cannot be computed without the average MPG of your vehicle. Would you like to add a vehicle?";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
            .setPositiveButton("YES", new DialogInterface.OnClickListener() 
            {   @Override
                public void onClick(DialogInterface dialog, int which) 
                { 
                    Intent intent = new Intent(context,CarInfoActivity.class);
                    startActivity(intent);
                    finish();
                }
            })
            .setNegativeButton("NO", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) 
                {
                    dialog.dismiss();
                } 
            });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getResults(double lat, double lng) {
        String selectedFuelType = fuelType.getSelectedItem().toString();
        ArrayList<GasStation> stations = StationRequest.NearbyGasStations(lat,
                lng, 10.0, selectedFuelType);
        ArrayList<Double> adjustedPrices = new ArrayList<Double>();
        for (GasStation s : stations) {
            double adjustedPrice = MPG == 0 ? 0.0 : Calculations.calculate(MPG, s.getFuelPrice(),
                    s.getDistance(), 15);
            adjustedPrices.add(adjustedPrice);
        }
        Intent i = new Intent(getContext(), SearchResultsView.class);
        i.putParcelableArrayListExtra("data", stations);
        i.putExtra("adjustedPrices", adjustedPrices);
        i.putExtra("latitude", lat);
        i.putExtra("longitude", lng);
        startActivity(i);
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
            i.putExtra("latitude", latitude);
            i.putExtra("longitude", longitude);
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
