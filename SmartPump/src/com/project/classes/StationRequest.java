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
    private static String myGasFeedkey = "rfej9napna";
    private static String myGasFeedProdutionKey = "";
    private static String placesKey = "AIzaSyBafJ_CEAEyohPS09lCoJYcdefGuBa5WoE";
    
    private static String myGasFeedDevUrl = "http://devapi.mygasfeed.com/";
    private static String myGasFeedUrl = "http://api.mygasfeed.com/";
    private static String placeSearch = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static String placeDetails = "https://maps.googleapis.com/maps/api/place/details/json?";
    
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
    
    private static String getPlaceReference(String address, String lat, String lng)
    {
        StringBuilder url = new StringBuilder(placeSearch);
        url.append("location=" + lat + "," + lng);
        url.append("&radius=5&sensor=false&key=" + placesKey);
        
        try 
        {
            URL requestUrl = new URL(url.toString());
            JSONObject placeInfo = getJson(requestUrl);
            JSONArray places = (JSONArray) placeInfo.get("results");
            if (places.size() == 0) return null;
            Iterator i = places.iterator();
            while(i.hasNext())
            {
                JSONObject place = (JSONObject) i.next();
                String formattedAddress = (String) place.get("formatted_address");
                if(formattedAddress.equals(address))
                {
                    System.out.println("found a ref");
                    return (String) place.get("reference");
                }
            }
            return null;
        } 
        catch (MalformedURLException e1) 
        {
            e1.printStackTrace();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    private static String getPhoneNumber(String address, double latitude, double longitude)
    {
        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);
        String ref = getPlaceReference(address,lat,lng);
        
        if (ref == null)
        {
            return null;
        }
        
        StringBuilder url = new StringBuilder(placeDetails);
        url.append("reference=" + ref);
        url.append("&sensor=false&key=" + placesKey);
        
        try 
        {
            URL requestUrl = new URL(url.toString());
            JSONObject placeInfo = getJson(requestUrl);
            JSONObject placeDetails = (JSONObject) placeInfo.get("result");
            String phone = (String) placeDetails.get("formatted_phone_number");
            return phone;
        } 
        catch (MalformedURLException e1) 
        {
            e1.printStackTrace();
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
        
        String formattedAddress = address + ", " + city + ", " + state;
        String phone = getPhoneNumber(formattedAddress, lat,lng);
        if(phone==null) phone = "Not Available";
        else System.out.println(phone);
        
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
        url.append(myGasFeedkey + ".json");
        
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
        url.append("/" + myGasFeedkey + ".json");
        
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
