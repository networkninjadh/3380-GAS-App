package com.project.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class FavoritesManager 
{
    private PreferencesHelper helper;
    private Set<String> favorites;
    private String favoritesKey = "stationId";
    
    public FavoritesManager(Context context)
    {
        this.helper = new PreferencesHelper(context);
        this.favorites = helper.GetPreferenceStringSet(favoritesKey);
    }
    public void addFavorite(GasStation station)
    {
        favorites.add(station.getStationId());
        helper.SavePreferenceStringSet(favoritesKey, favorites);
    }
    
    public void removeFavorite(GasStation station)
    {
        favorites.remove(station.getStationId());
        helper.SavePreferenceStringSet(favoritesKey, favorites);
    }
    
    public boolean checkForFavorite(GasStation station)
    {
        return favorites.contains(station.getStationId());
    }
    
    public ArrayList<String> getFavorites()
    {
        if (favorites != null)
        {
            return new ArrayList<String> (favorites);
        }
        
        return null;
    }
}
