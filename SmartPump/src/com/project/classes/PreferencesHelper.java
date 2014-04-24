package com.project.classes;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesHelper {

    private SharedPreferences sharedPreferences;
    private Editor editor;

    public PreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("SmartPumpData",context.MODE_PRIVATE);    
        this.editor = sharedPreferences.edit();
    }

    public String GetPreferences(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void SavePreferences(String key, String value) {
        editor.putString(key, value);    
        editor.commit();  
    }
    
    public Set<String> GetPreferenceStringSet(String key) {
        return sharedPreferences.getStringSet(key, new HashSet<String>());
    }
    
    public void SavePreferenceStringSet(String key, Set<String> set) {
        editor.putStringSet(key, set);
        editor.commit();
    }
} 