package com.project.smartpump;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.project.classes.GasStation;
import com.project.classes.StationRequest;
import com.project.classes.StationSearchResult;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SearchResultsView extends Activity 
{
	public static Context context;
    private ArrayList<StationSearchResult> stations;
    private ArrayList<String> searchResults;
    //private ArrayList<Double> adjustedPrices;
    private double currentLat, currentLng;
    static ListView results;
    RadioButton radio_sort_button;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_view);
        context = getApplicationContext();
        
        // Activate Clickable Icon Button
        ActionBar smartPumpIcon = getActionBar();
        smartPumpIcon.setDisplayHomeAsUpEnabled(true);
        
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
        
        //Attempt to sort stations
        Collections.sort(stations, StationSearchResult.StationSearchResultComparator);
        
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
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_sort);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
        	public void onCheckedChanged(RadioGroup group, int checkedId)
        	{
        		radio_sort_button = (RadioButton) findViewById(checkedId);
        		//test the radio button
        		Toast.makeText(context, radio_sort_button.getText(), Toast.LENGTH_SHORT).show();
        	}
        });
        System.out.println("Got to end of create");
    }
    
    // -------------------------- OPTIONS MENU----------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.results_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	this.finish();
    	return true;
    }

}

