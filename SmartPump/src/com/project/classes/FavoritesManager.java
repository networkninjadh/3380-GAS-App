package com.project.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class FavoritesManager 
{
    static String PREFS = "smartpumpFavorites";
    
    public static void addFavorite(Context c, GasStation station)
    {
        SharedPreferences favorites = c.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = favorites.edit();
        Set<String> favId = favorites.getStringSet("stationId", new HashSet<String>());
        
        favId.add(station.getStationId());
        
        editor.putStringSet("stationId", favId);
        editor.commit();
    }
    
    public static void removeFavorite(Context c, GasStation station)
    {
        SharedPreferences favorites = c.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = favorites.edit();
        Set<String> favId = favorites.getStringSet("stationId", new HashSet<String>());
        
        favId.remove(station.getStationId());
        
        editor.putStringSet("stationId", favId);
        editor.commit();
    }
    
    public static ArrayList<String> getFavorites(Context c)
    {
        SharedPreferences favorites = c.getSharedPreferences(PREFS, 0);
        Set<String> favId = favorites.getStringSet("stationId", new HashSet<String>());
        if (favId != null)
        {
            return new ArrayList<String> (favId);
        }
        
        return null;
    }
}
