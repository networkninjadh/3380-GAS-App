package com.project.classes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

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
        String json = "";
        try 
        {
            URL requestUrl = new URL(url.toString());
            GetJsonTask getJson = new GetJsonTask();
            //getJson.execute(requestUrl);
            json = getJson.execute(requestUrl).get();
        } 
        catch (MalformedURLException e1) 
        {
            e1.printStackTrace();
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        } 
        catch (ExecutionException e) 
        {
            e.printStackTrace();
        }
        
        System.out.println(json);
        
        try
        {
            JSONParser parser = new JSONParser();
            Object responseObject = parser.parse(json);
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
                
                //TODO: Find a better way to handle null price value
                String sPrice = (String) station.get("price");
                double price = (sPrice.equals("N/A")) ? 0.0 : Double.parseDouble((String) station.get("price"));
                
                int zip = Integer.parseInt((String) station.get("zip"));
                double lat = Double.parseDouble((String) station.get("lat"));
                double lng = Double.parseDouble((String) station.get("lng"));
                String phone = ""; //this is not returned by mygasfeed
                gasStations.add(new GasStation(price, phone, address, city, state, zip, lat, lng));
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
