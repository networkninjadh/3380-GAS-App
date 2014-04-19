package com.project.smartpump;

import java.util.ArrayList;

import com.project.classes.FavoritesManager;
import com.project.classes.StationRequest;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoritesActivity extends ListActivity {

    public static Context context;
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
        
        ArrayList<String> favId = FavoritesManager.getFavorites(context);
        ArrayList<String> favorites = new ArrayList<String>();
        
        System.out.println("Got Ids");
        if(favId != null)
        {
            for (String id: favId)
            {
                favorites.add((StationRequest.getStationById(id)).getWebAddress());
            }
        }
        else
        {
            favorites.add("No saved favorites");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.favorite_item, favorites);
        lvFavs.setAdapter(adapter);
    }
}
