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
import android.widget.Adapter;
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

    SearchResultsAdapter adapter;


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

        System.out.println("Finished setting up results");
        
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResults);
        adapter = new SearchResultsAdapter(stations, context);
        System.out.println("Created adapter");
        adapter.DistanceSort();
    	adapter.notifyDataSetChanged();
        
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
    	switch (item.getItemId()) {
    	case android.R.id.home:
        	this.finish();
        	return true;
        case R.id.adj_sort:
        	adapter.AdjSort();
        	adapter.notifyDataSetChanged();
            return true;
        case R.id.dis_sort:
        	adapter.DistanceSort();
        	adapter.notifyDataSetChanged();
        	return true;
        case R.id.price_sort:
        	adapter.PriceSort();
        	adapter.notifyDataSetChanged();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}

