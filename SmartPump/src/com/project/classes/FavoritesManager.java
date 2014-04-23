package com.project.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class FavoritesManager 
{
    //static String PREFS = "smartpumpFavorites";
    //private SharedPreferences favorites;
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
        //SharedPreferences favorites = c.getSharedPreferences(PREFS, 0);
        //SharedPreferences.Editor editor = favorites.edit();
        //Set<String> favId = favorites.getStringSet("stationId", new HashSet<String>());
        
        //Set<String> favId = helper.GetPreferenceStringSet(favoritesKey);
        favorites.add(station.getStationId());
        helper.SavePreferenceStringSet(favoritesKey, favorites);
        
        //editor.putStringSet("stationId", favId);
        //editor.commit();
    }
    
    public void removeFavorite(GasStation station)
    {
        //SharedPreferences favorites = c.getSharedPreferences(PREFS, 0);
        //SharedPreferences.Editor editor = favorites.edit();
        //Set<String> favId = favorites.getStringSet("stationId", new HashSet<String>());
        
        //favId.remove(station.getStationId());
        favorites.remove(station.getStationId());
        helper.SavePreferenceStringSet(favoritesKey, favorites);
        
        //editor.putStringSet("stationId", favId);
        //editor.commit();
    }
    
    public boolean checkForFavorite(GasStation station)
    {
        //SharedPreferences favorites = c.getSharedPreferences(PREFS, 0);
        //Set<String> favId = favorites.getStringSet("stationId", new HashSet<String>());
        return favorites.contains(station.getStationId());
    }
    
    public ArrayList<String> getFavorites()
    {
        //SharedPreferences favorites = c.getSharedPreferences(PREFS, 0);
        //Set<String> favId = favorites.getStringSet("stationId", new HashSet<String>());
        if (favorites != null)
        {
            return new ArrayList<String> (favorites);
        }
        
        return null;
    }
}
