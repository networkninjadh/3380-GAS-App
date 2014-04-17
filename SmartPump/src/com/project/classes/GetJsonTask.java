package com.project.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.os.AsyncTask;

public class GetJsonTask extends AsyncTask<URL, Void, String> 
{
    protected String doInBackground(URL... urls)
    {
        InputStream dataStream;
        StringBuilder response = new StringBuilder();
        try 
        {
            dataStream = urls[0].openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(dataStream));
            int cp;
            while ((cp = rd.read()) != -1) 
            {
                response.append((char) cp);
            }
            return response.toString();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
