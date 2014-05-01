package com.project.smartpump;

import java.text.NumberFormat;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.classes.Calculations;
import com.project.classes.FavoritesManager;
import com.project.classes.FuelPrice;
import com.project.classes.GasStation;
import com.project.classes.StationSearchResult;

public class StationDetailsActivity extends Activity {
    public static Context context;
    private GoogleMap gMap;
    private static RelativeLayout priceSummary, 
                                  fuelSelection;
    private static ImageView logo;
    private static TextView name, 
                            address, 
                            cityStateZip, 
                            phone, 
                            price,
                            adjustedPrice, 
                            distance;
    private static Button chooseStation, priceRequest;
    private static Spinner fuelType;
    private static EditText numGallons;
    private boolean spinnerInitialized = false;
    
    private FavoritesManager favoriteManager;
    private boolean isFavorite;
    
    private GasStation station;
    private double currentLat, 
                   currentLng, 
                   stationLat, 
                   stationLng;
    private double MPG, 
                   distanceAway,
                   adjustedCost;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_details_view);
        context = getApplicationContext();
        favoriteManager = new FavoritesManager(context);
        
        // Activate Clickable Icon Button
        ActionBar smartPumpIcon = getActionBar();
        smartPumpIcon.setDisplayHomeAsUpEnabled(true);

        gMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.miniMap)).getMap();
        gMap.setMyLocationEnabled(true);

        priceSummary = (RelativeLayout) findViewById(R.id.priceSummary);
        fuelSelection = (RelativeLayout) findViewById(R.id.fuelSelection);
        logo = (ImageView) findViewById(R.id.stationLogo);
        fuelType = (Spinner) findViewById(R.id.fuelType);
        numGallons = (EditText) findViewById(R.id.estimatedGallons);
        priceRequest = (Button) findViewById(R.id.submit);
        
        name = (TextView) findViewById(R.id.stationName);
        address = (TextView) findViewById(R.id.stationAddress);
        cityStateZip = (TextView) findViewById(R.id.cityStateZip);
        // phone = (TextView)findViewById(R.id.stationPhone);
        price = (TextView) findViewById(R.id.stationPrice);
        adjustedPrice = (TextView) findViewById(R.id.stationAdjustedCost);
        distance = (TextView) findViewById(R.id.distanceAway);
        chooseStation = (Button) findViewById(R.id.ChooseDestination);

        chooseStation.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?" + "saddr="+ currentLat + "," + currentLng + "&daddr=" + stationLat + "," + stationLng));
                intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        
        // Get station and price information from intent
        boolean fuelSelected = this.getIntent().getExtras()
                .getBoolean("fuelTypeSelected");
        if (fuelSelected)
        {
            StationSearchResult selected = this.getIntent().getExtras().getParcelable("resultSelected");
            station  = selected.getStation();
            adjustedCost = selected.getAdjustedCost();
        }
        else
        {
            station = this.getIntent().getExtras().getParcelable("stationSelected");
            MPG = this.getIntent().getExtras().getDouble("MPG");
        }
        
        // Set station logo
        setLogo();
        
        // Get current location
        currentLat = this.getIntent().getExtras().getDouble("latitude");
        currentLng = this.getIntent().getExtras().getDouble("longitude");

        // Add marker to mini map
        stationLat = station.getCoords().latitude;
        stationLng = station.getCoords().longitude;
        String title = station.getStationName();
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(stationLat, stationLng)).title(title)
                .icon(BitmapDescriptorFactory.defaultMarker());
        gMap.addMarker(marker);

        // Adjust camera to marker location
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(
                stationLat, stationLng), 10);
        gMap.animateCamera(location);

        // Compute distance if necessary
        distanceAway = station.getDistance();
        if (distanceAway == 0.0) {
            Location start = new Location("Start");
            start.setLatitude(currentLat);
            start.setLongitude(currentLng);
            Location finish = new Location("Finish");
            finish.setLatitude(stationLat);
            finish.setLongitude(stationLng);
            // distanceTo() returns distance in meters, multiplication to
            // convert to miles
            double d = start.distanceTo(finish) * 0.000621371;
            distanceAway = Math.round(d * 100.0) / 100.0;
        }

        // Add station details to display
        name.setText(station.getStationName());
        address.setText(station.getStationAddress().getStreet());
        cityStateZip.setText(station.getStationAddress().getCity() + ", " + station.getStationAddress().getState()
                + " " + station.getStationAddress().getZip());
        // phone.setText(station.getPhoneNumber());
        distance.setText(String.valueOf(distanceAway) + " miles");

        // Price information
        if (fuelSelected) 
        {
            fuelSelection.setVisibility(View.GONE);
            priceSummary.setVisibility(View.VISIBLE);
            double fuelPrice = station.getSelectedFuelPrice().getPrice();
            setPriceDisplays(fuelPrice, adjustedCost);
        } 
        else 
        {
            // Hide price summary from display
            priceSummary.setVisibility(View.GONE);
            fuelSelection.setVisibility(View.VISIBLE);

            // Set up spinner menu for selecting fuel type
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.fuel_types,
                            android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fuelType.setAdapter(adapter);
            fuelType.setSelection(0);
            // Set up listener for price request
            priceRequest.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPriceSummary();
                }
                
            });
        }
    }
    
    private void setLogo() 
    {
        String name = station.getStationName();
        name = (name.replace(" ","")).toLowerCase();
        System.out.println("Name: " + name);
        int logoId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        System.out.println("Id:" + String.valueOf(logoId));
        if (logoId != 0)
        {
            logo.setImageDrawable(context.getResources().getDrawable(logoId));
        }
        
    }

    // -----------------------PRICE DISPLAY HELPERS--------------------------
    private void getPriceSummary()
    {
        final int selectedFuel = fuelType.getSelectedItemPosition();
        if (selectedFuel == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please select a fuel type").setPositiveButton(
                    "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else if ((numGallons.getText().toString()).equals("")) {
            String message = "If you wish to see adjusted fuel costs, please cancel this search"
                    + " and enter the number of gallons of fuel you expect to purchase.";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setPositiveButton("CONTINUE",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,int which) 
                                {
                                    FuelPrice price = getFuelSelection(selectedFuel);
                                    setPriceDisplays(price, 0.0);
                                }
                            })
                    .setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        } 
        else 
        {
            FuelPrice price = getFuelSelection(selectedFuel);
            double gallons = Double.parseDouble(numGallons.getText().toString());
            setPriceDisplays(price, gallons);
        }
    }
    
    private FuelPrice getFuelSelection(int position)
    {
        switch (position) {
        case 1:
            return station.getRegPrice();
        case 2:
            return station.getMidPrice();
        case 3:
            return station.getPrePrice();
        case 4:
            return station.getDieselPrice();
        default:
            return null;        
        }
    }
    
    private void setPriceDisplays(FuelPrice price, double gallons) {
        double fuelPrice = price.getPrice();
        double adjustPrice = MPG == 0.0 || gallons == 0 ? 0.0 : Calculations.calculate(MPG,
                fuelPrice, distanceAway, gallons);
        setPriceDisplays(fuelPrice, adjustPrice);

    }

    private void setPriceDisplays(double fuelPrice, double adjustPrice) {
        // Determine price information
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String sPrice, sAdjustPrice;
        if (fuelPrice == 0.0) {
            sPrice = "Not Available";
            price.setTextSize(12);
        } else {
            sPrice = currency.format(fuelPrice);
        }
        if (adjustPrice == 0.0) {
            sAdjustPrice = "Not Available";
            adjustedPrice.setTextSize(12);
        } else {
            sAdjustPrice = currency.format(adjustPrice);
        }

        // Add station details to display
        price.setText(sPrice);
        adjustedPrice.setText(sAdjustPrice);
        fuelSelection.setVisibility(View.GONE);
        priceSummary.setVisibility(View.VISIBLE);
    }
    
    // -------------------------- OPTIONS MENU----------------------------
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);

        // Check if the station is already saved as a favorite
        isFavorite = favoriteManager.checkForFavorite(station);
        if (isFavorite) {
            // If it is a favorite, display yellow star
            Drawable favorite = getResources().getDrawable(
                    android.R.drawable.btn_star_big_on);
            MenuItem star = menu.findItem(R.id.favorite);
            star.setIcon(favorite);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case android.R.id.home:
        	this.finish();
        	return true;
        case R.id.favorite:
            String message = isFavorite ? "Remove station as a favorite?"
                    : "Save station as a favorite?";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    if (!isFavorite) {
                                        // Add to saved favorites
                                        favoriteManager.addFavorite(station);
                                        // Replace gray star with yellow star
                                        Drawable favorite = getResources()
                                                .getDrawable(
                                                        android.R.drawable.btn_star_big_on);
                                        item.setIcon(favorite);
                                    } else {
                                        // Remove from saved favorites
                                        favoriteManager.removeFavorite(station);
                                        // Replace yellow star with gray star
                                        Drawable favorite = getResources()
                                                .getDrawable(
                                                        android.R.drawable.btn_star_big_off);
                                        item.setIcon(favorite);
                                    }
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
