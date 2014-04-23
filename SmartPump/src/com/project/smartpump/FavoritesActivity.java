package com.project.smartpump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.project.classes.FavoritesManager;
import com.project.classes.GasStation;
import com.project.classes.StationRequest;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class FavoritesActivity extends ListActivity {

    public static Context context;
    private FavoritesManager manager;
    private ArrayList<GasStation> favoriteStations;
    private double currentLat, currentLng;
    private double mpg;
    static ListView lvFavs;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        context = getApplicationContext();
        lvFavs = (ListView)findViewById(android.R.id.list);
        manager = new FavoritesManager(context);
        currentLat = this.getIntent().getExtras().getDouble("latitude");
        currentLng = this.getIntent().getExtras().getDouble("longitude");
        
        
        ArrayList<String> favId = manager.getFavorites();
        favoriteStations = new ArrayList<GasStation>();
        ArrayList<Map<String, String>> favDetails = new ArrayList<Map<String,String>>();
        
        System.out.println("Got Ids");
        if(favId != null)
        {
            for (String id: favId)
            {
                GasStation s = StationRequest.getStationById(id);
                favoriteStations.add(s);
                favDetails.add(putStation(s.getStationName(), s.getWebAddress()));;
            }
            String[] from = {"Name", "Address"};
            int[] to = { android.R.id.text1, android.R.id.text2 };
            
            SimpleAdapter adapter = new SimpleAdapter(this, favDetails,
                    android.R.layout.simple_list_item_2, from, to);
            lvFavs.setAdapter(adapter);
        }
        
        lvFavs.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {
                view.setSelected(true);
                GasStation selected = favoriteStations.get(position);
                double adjusted = 0.0;
                Intent i = new Intent(getContext(), StationDetailsActivity.class);
                i.putExtra("stationSelected", selected);
                i.putExtra("fuelTypeSelected", false);
                i.putExtra("latitude", currentLat);
                i.putExtra("longitude", currentLng);
                i.putExtra("adjustedPrice", adjusted);
                startActivity(i);
            }
        });
    }
    
    private HashMap<String, String> putStation(String name, String address)
    {
        HashMap<String,String> newItem = new HashMap<String, String>();
        newItem.put("Name", name);
        newItem.put("Address", address);
        return newItem;
    }
}
