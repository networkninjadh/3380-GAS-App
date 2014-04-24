package com.project.smartpump;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.project.classes.GasStation;
import com.project.classes.StationRequest;
import com.project.classes.StationSearchResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchResultsView extends Activity 
{
    public static Context context;
    private ArrayList<StationSearchResult> stations;
    private ArrayList<String> searchResults;
    //private ArrayList<Double> adjustedPrices;
    private double currentLat, currentLng;
    static ListView results;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_view);
        context = getApplicationContext();
        
        System.out.println("opening search results");
        results = (ListView)findViewById(android.R.id.list);
        stations = this.getIntent().getExtras().getParcelableArrayList("data");
        //adjustedPrices = (ArrayList<Double>) this.getIntent().getSerializableExtra("adjustedPrices");
        currentLat = this.getIntent().getExtras().getDouble("latitude");
        currentLng = this.getIntent().getExtras().getDouble("longitude");
        
        searchResults = new ArrayList<String>();
        if(stations != null)
        {
            int numStations = stations.size();
            for (int i = 0; i < numStations; ++i)
            {
                GasStation s = stations.get(i).getStation();
                double adjustedPrice = stations.get(i).getAdjustedCost();
                NumberFormat currency = NumberFormat.getCurrencyInstance();
                String priceDisplay = s.getSelectedFuelPrice().getPrice() == 0.0 ? "Not Available" :
                    currency.format(s.getSelectedFuelPrice().getPrice());
                String adjustedPriceDisplay = adjustedPrice == 0.0 ? "Not Available" :
                    currency.format(adjustedPrice);
                StringBuilder result = new StringBuilder(s.getStationName());
                result.append(" " + String.valueOf(s.getDistance()));
                result.append(" " + priceDisplay);
                result.append(" " + adjustedPriceDisplay);
                searchResults.add(result.toString());
            }
        }
        else
        {
            searchResults.add("No stations found");
        }

        System.out.println("Finished setting up results");
        
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResults);
        SearchResultsAdapter adapter = new SearchResultsAdapter(stations, context);
        System.out.println("Created adapter");
       
        results.setAdapter(adapter);  
        System.out.println("Set adapter");
        
        results.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {
                view.setSelected(true);
                StationSearchResult selected = stations.get(position);
                //double adjusted = adjustedPrices.get(position);
                Intent i = new Intent(getContext(), StationDetailsActivity.class);
                i.putExtra("resultSelected", selected);
                i.putExtra("fuelTypeSelected", true);
                i.putExtra("latitude", currentLat);
                i.putExtra("longitude", currentLng);
                //i.putExtra("adjustedPrice", adjusted);
                startActivity(i);
            }
        });
        
        System.out.println("Got to end of create");
    }

}

