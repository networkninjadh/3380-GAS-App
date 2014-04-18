package com.project.classes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

public class StationRequest {
    private static String APIkey = "rfej9napna";
    private static String myGasFeedUrl = "http://devapi.mygasfeed.com/";
    
    private static JSONObject getJson(URL requestUrl)
    {
        String json = "";
        try 
        {
            GetJsonTask getJson = new GetJsonTask();
            json = getJson.execute(requestUrl).get();
            System.out.println(json);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        } 
        catch (ExecutionException e) 
        {
            e.printStackTrace();
        }
        
        JSONParser parser = new JSONParser();
        Object responseObject;
        try 
        {
            responseObject = parser.parse(json);
            return (JSONObject) responseObject;
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    private static GasStation mapJsonToStation(JSONObject station, boolean requestById)
    {
        String address = (String) station.get("address");
        String city = (String) station.get("city");
        String state = (String) station.get("region");
        int zip = Integer.parseInt((String) station.get("zip"));
        double lat = Double.parseDouble((String) station.get("lat"));
        double lng = Double.parseDouble((String) station.get("lng"));
        String id = (String) station.get("id");
        String phone = ""; //this is not returned by mygasfeed
        
        String name;
        String sPrice;
        if(requestById)
        {
            sPrice = (String) station.get("reg_price");
            name = (String) station.get("station_name");
        }
        else
        {
            sPrice = (String) station.get("price");
            name = (String) station.get("station");
        }
        
        //TODO: Find a better way to handle null price value
        double price = (sPrice.equals("N/A")) ? 0.0 : Double.parseDouble(sPrice);
        
        return new GasStation(price, name, phone, address, city, state, zip, lat, lng, id);
    }

    public static ArrayList<GasStation> NearbyGasStations(double latitude, double longitude, double radius, String fuelType)
    {
        String sortby = "Price";
        StringBuilder url = new StringBuilder(myGasFeedUrl);
        url.append("stations/radius/");
        url.append(latitude + "/" + longitude + "/" + radius + "/");
        url.append(fuelType + "/" + sortby + "/");
        url.append(APIkey + ".json");
        
        JSONArray stations = new JSONArray();
        try 
        {
            URL requestUrl = new URL(url.toString());
            JSONObject stationInfo = getJson(requestUrl);
            stations = (JSONArray) stationInfo.get("stations"); 
        } 
        catch (MalformedURLException e1) 
        {
            e1.printStackTrace();
        }
        
        Iterator i = stations.iterator();
        ArrayList<GasStation> gasStations = new ArrayList<GasStation>();
        while(i.hasNext())
        {
            JSONObject station = (JSONObject) i.next();
            gasStations.add(mapJsonToStation(station, false));
        }
        
        return gasStations;
    }

    public static LatLng getGeoCoordsFromAddress(Context c, String address)
    {
        Geocoder geocoder = new Geocoder(c);
        List<Address> addresses;
        try 
        {
            addresses = geocoder.getFromLocationName(address, 1);
            if(addresses.size() > 0)
            {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                System.out.println(latitude);
                System.out.println(longitude);
                return new LatLng(latitude, longitude);
            }
            else
            {
                return null;
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public static GasStation getStationById(String id)
    {
        StringBuilder url = new StringBuilder(myGasFeedUrl);
        url.append("stations/details/" + id);
        url.append("/" + APIkey + ".json");
        
        try 
        {
            URL requestUrl = new URL(url.toString());
            JSONObject stationInfo = getJson(requestUrl);
            JSONObject details = (JSONObject) stationInfo.get("details");
            
            return mapJsonToStation(details, true);
        } 
        catch (MalformedURLException e1) 
        {
            e1.printStackTrace();
        }
        
        return null;
    }
}
