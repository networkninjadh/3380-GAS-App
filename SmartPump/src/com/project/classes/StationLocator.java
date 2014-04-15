package com.project.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StationLocator {
    public static ArrayList<GasStation> NearbyGasStations(double latitude, double longitude, double radius, String fuelType)
    {
        String sortby = "Price";
        String devAPIkey = "rfej9napna";
        StringBuilder url = new StringBuilder("http://devapi.mygasfeed.com/stations/radius/");
        url.append(latitude + "/" + longitude + "/" + radius + "/");
        url.append(fuelType + "/" + sortby + "/");
        url.append(devAPIkey + ".json");
        
        InputStream dataStream;
        StringBuilder response = new StringBuilder();
        try 
        {
            dataStream = new URL(url.toString()).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(dataStream));
            int cp;
            while ((cp = rd.read()) != -1) 
            {
                response.append((char) cp);
            }
        } 
        catch (MalformedURLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try
        {
            JSONParser parser = new JSONParser();
            Object responseObject = parser.parse(response.toString());
            JSONObject stationInfo = (JSONObject) responseObject;
            JSONArray stations = (JSONArray) stationInfo.get("stations"); 
            Iterator i = stations.iterator();
            ArrayList<GasStation> gasStations = new ArrayList<GasStation>();
            while(i.hasNext())
            {
                JSONObject station = (JSONObject) i.next();
                String address = (String) station.get("address");
                String city = (String) station.get("city");
                String state = (String) station.get("region");
                double price = Double.parseDouble((String) station.get("price"));
                int zip = Integer.parseInt((String) station.get("zip"));
                String phone = ""; //this is not returned by mygasfeed
                gasStations.add(new GasStation(price, phone, address, city, state, zip));
            }
            
            return gasStations;
        } 
        catch (ParseException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
