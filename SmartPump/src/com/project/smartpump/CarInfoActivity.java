/**
 * @author Damond Howard
 */
package com.project.smartpump;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.classes.NoDefaultSpinner;
import com.project.classes.PreferencesHelper;
import com.project.classes.Vehicle;

public class CarInfoActivity extends Activity implements OnItemSelectedListener 
{   
    Context context = this;
    private static final String TAG = "Fuel Economy Query";
    private static final String SERVER_URL = "http://www.fueleconomy.gov/ws/rest/vehicle/menu/";
    private static String full_URL1;
    private static String full_URL2;
    private static String full_URL3;
    private static String full_URL4;
    
    private static ArrayList<String> SpinnerList1 = new ArrayList<String>();
    private static ArrayList<String> SpinnerList2 = new ArrayList<String>();
    private static ArrayList<String> SpinnerList3 = new ArrayList<String>();
    private static ArrayList<String> VehicleIDList = new ArrayList<String>();
    private static URL FedGov; 
    private static Boolean falloutbool = false;
    private boolean firstRunCheckYearSpinner = true;
    private boolean firstRunCheckMakeSpinner = true;
    private boolean firstRunCheckModelSpinner = true;
    private boolean firstRunCheckOptionSpinner = true; 
    
    
    Button AddVehicle,Reset;
    Spinner  make_spinner, model_spinner,options_spinner;
    NoDefaultSpinner year_spinner;
    String make,model,year;
    String vID;
    
    String MPG;
    boolean profileWasMade = false;
    
    private static ArrayAdapter<CharSequence> adapter = null, makeAdapter = null,modelAdapter = null, optionsAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {   super.onCreate(savedInstanceState);
        final PreferencesHelper prefs = new PreferencesHelper(this);
        
        // Activate Clickable Icon Button
        ActionBar smartPumpIcon = getActionBar();
        smartPumpIcon.setDisplayHomeAsUpEnabled(true);
        
        
        System.out.println("Initializing components in car info");
        setContentView(R.layout.car_info);
        AddVehicle   = (Button)findViewById(R.id.AddV);
        Reset        = (Button)findViewById(R.id.ResetV);
        year_spinner = (NoDefaultSpinner)findViewById(R.id.spinnerYear); 
        make_spinner = (Spinner)findViewById(R.id.spinnerMake); 
        model_spinner = (Spinner)findViewById(R.id.spinnerModel);
        options_spinner = (Spinner)findViewById(R.id.spinnerOptions);
        System.out.println("Got past view finding");
        make_spinner.setEnabled(false);   
        make_spinner.setClickable(false);  
        model_spinner.setEnabled(false);   
        model_spinner.setClickable(false);
        options_spinner.setEnabled(false);   
        options_spinner.setClickable(false); 
        
        adapter = ArrayAdapter.createFromResource(CarInfoActivity.this, R.array.years_spinner, android.R.layout.simple_spinner_dropdown_item
                );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
    
        year_spinner.setAdapter(adapter);
        year_spinner.setPrompt("Select Vehicle Year");
        year_spinner.setSelection(-1);
        
        System.out.println("Making item selector 1");
        year_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                year = year_spinner.getSelectedItem().toString();
                Log.i(TAG, "Query Database...");
                
                StringBuilder RequestURL = new StringBuilder(SERVER_URL);
                RequestURL.append("make?year=" + year_spinner.getSelectedItem());
                full_URL1 = RequestURL.toString();

                AsyncDownloader downloader = new AsyncDownloader(); 
                downloader.execute();
                try {
                    downloader.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String[] MakeLists = new String[SpinnerList1.size()];
                MakeLists = SpinnerList1.toArray(MakeLists);
                
                makeAdapter = new ArrayAdapter<CharSequence>(CarInfoActivity.this, android.R.layout.simple_spinner_dropdown_item
                        , MakeLists);
                // Specify the layout to use when the list of choices appears
                makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                makeAdapter.notifyDataSetChanged();
                make_spinner.setEnabled(true);   
                make_spinner.setClickable(true); 
                make_spinner.setAdapter(makeAdapter);   
                make_spinner.setSelection(-1, false);
                try{
                    makeAdapter.notifyDataSetChanged();
                    modelAdapter.notifyDataSetChanged();
                    optionsAdapter.notifyDataSetChanged();
                    
                }catch(Exception e){
                    
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        make_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                
                SpinnerList2.clear();
                SpinnerList3.clear();
                try{
                    modelAdapter.notifyDataSetChanged();
                    optionsAdapter.notifyDataSetChanged();
                }catch(Exception e){
                    
                }
                try {
                    make = URLEncoder.encode(make_spinner.getSelectedItem().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Log.i(TAG, "Query Database 2...");
                
                StringBuilder ModelURL = new StringBuilder(SERVER_URL);
                ModelURL.append("model?year=" + year + "&make=" + make);
                full_URL2 = new String(ModelURL.toString());
                
                AsyncDownloader1 modelDownloader = new AsyncDownloader1(); 
                modelDownloader.execute();
                try {
                    modelDownloader.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                
                String[] ModelLists = new String[SpinnerList2.size()];
                ModelLists = SpinnerList2.toArray(ModelLists);
                
                modelAdapter = new ArrayAdapter<CharSequence>(CarInfoActivity.this, android.R.layout.simple_spinner_item
                        , ModelLists);
                // Specify the layout to use when the list of choices appears
                modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                model_spinner.setEnabled(true);   
                model_spinner.setClickable(true);               
                model_spinner.setAdapter(modelAdapter); 
                model_spinner.setSelection(0, true);
//              android.os.SystemClock.sleep(1000);
                try{
                    makeAdapter.notifyDataSetChanged();
                    modelAdapter.notifyDataSetChanged();
                    optionsAdapter.notifyDataSetChanged();
                    
                }catch(Exception e){
                    
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        System.out.println("Making item selector 2");
        model_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                
                try{
                    optionsAdapter.notifyDataSetChanged();
                }catch(Exception e){
                    
                }
                
                try {
                    model =  URLEncoder.encode(model_spinner.getSelectedItem().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                Log.i(TAG, "Query Database 3...");
                
                StringBuilder ModelURL = new StringBuilder(SERVER_URL);
                ModelURL.append("options?year=" + year + "&make=" + make + "&model=" + model);
                full_URL3 = new String(ModelURL.toString());
                
                AsyncDownloader2 optionsDownloader = new AsyncDownloader2(); 
                optionsDownloader.execute();
                try {
                    optionsDownloader.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//                  android.os.SystemClock.sleep(1000);
                
                String[] OptionsLists = new String[SpinnerList3.size()];
                OptionsLists = SpinnerList3.toArray(OptionsLists);
                
                optionsAdapter = new ArrayAdapter<CharSequence>(CarInfoActivity.this, android.R.layout.simple_spinner_item
                        , OptionsLists);
                // Specify the layout to use when the list of choices appears
                optionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                options_spinner.setEnabled(true);   
                options_spinner.setClickable(true);     
                options_spinner.setAdapter(optionsAdapter); 
                options_spinner.setSelection(0);
                

//                  android.os.SystemClock.sleep(2000);
                optionsAdapter.notifyDataSetChanged();
                try{
                    makeAdapter.notifyDataSetChanged();
                    modelAdapter.notifyDataSetChanged();
                    optionsAdapter.notifyDataSetChanged();
                    
                }catch(Exception e){
                    
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        System.out.println("Making item selector 3");
        options_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
            
                    
                    vID =  VehicleIDList.get(options_spinner.getSelectedItemPosition());
                    full_URL4 = "http://www.fueleconomy.gov/ws/rest/vehicle/" + vID;
                    AsyncDownloader3 MPGDownloader = new AsyncDownloader3();
                    MPGDownloader.execute();
                    try {
                        MPGDownloader.get();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }   
        });
        
        AddVehicle.setOnClickListener(new OnClickListener()
        {
            @SuppressLint("ShowToast")
			@Override
            public void onClick(View v) {
                prefs.SavePreferences("VehicleID", vID);
                prefs.SavePreferences("VehicleMPG", MPG);
                Toast toast = Toast.makeText(context, "Vehicle gets " + MPG + " MPG", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent MainActivity = new Intent(CarInfoActivity.this, MainActivity .class);
                startActivity(MainActivity);
            }   
        
        });
        Reset.setOnClickListener(new OnClickListener()
        {   @Override
            public void onClick(View v) 
            {   
                reset();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {   getMenuInflater().inflate(R.menu.car_info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {   switch (item.getItemId())
        {
        case android.R.id.home:
        	if (profileWasMade) //if a profile exists or a new one was made
            {   
                this.finish();
            }
            else //do this when nothing was pressed error in here
            {   String message = "A Vehicle must be selected to get the adjusted" +
                        " prices. You may reselect later from the settings menu.";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() 
                    {   @Override
                        public void onClick(DialogInterface dialog, int which) 
                        {
	                    	Intent intent = new Intent(context,MainActivity.class);
	                        startActivity(intent);
                            finish();
                        }
                    });
                AlertDialog alert = builder.create();
                alert.show();
            }
        	break;
    	case R.id.action_settings:
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
            break;
        
        default:
        break;
        }
        return true;
    }
    /**
     * takes in vehicle information and passes it to the database
     * @param vehicleYear
     * @param vehicleMake
     * @param vehicleModel
     * @param VehicleID
     */

    public void reset()

    {   
        SpinnerList1.clear();
        SpinnerList2.clear();
        SpinnerList3.clear();
        
        try {
            makeAdapter.notifyDataSetChanged();
            modelAdapter.notifyDataSetChanged();
            optionsAdapter.notifyDataSetChanged();
            
        }catch (Exception e) {
            
        }
        
        firstRunCheckYearSpinner = true;
        firstRunCheckMakeSpinner = true;
        firstRunCheckModelSpinner = true;
        make_spinner.setEnabled(false);   
        make_spinner.setClickable(false);  
        model_spinner.setEnabled(false);   
        model_spinner.setClickable(false);
        options_spinner.setEnabled(false);   
        options_spinner.setClickable(false); 
        year_spinner.setSelection(0);
        make_spinner.setSelection(0);
        model_spinner.setSelection(0);
    }
    
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (parent.getId()) {
        case R.id.spinnerYear:
        
            year = year_spinner.getSelectedItem().toString();
            Log.i(TAG, "Query Database...");
            
            StringBuilder RequestURL = new StringBuilder(SERVER_URL);
            RequestURL.append("make?year=" + year_spinner.getSelectedItem());
            full_URL1 = RequestURL.toString();
            
            AsyncDownloader downloader = new AsyncDownloader(); 
            downloader.execute();

            
            String[] MakeLists = new String[SpinnerList1.size()];
            MakeLists = SpinnerList1.toArray(MakeLists);
            
            ArrayAdapter<String> makeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item
                    , MakeLists);
            // Specify the layout to use when the list of choices appears
            makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            makeAdapter.notifyDataSetChanged();
            make_spinner.setAdapter(makeAdapter);
            break;
        case R.id.spinnerMake:
            make = make_spinner.getSelectedItem().toString();
            Log.i(TAG, "Query Database 2...");
            
            StringBuilder ModelURL = new StringBuilder(SERVER_URL);
            ModelURL.append("model?year=" + year + "&make=" + make);
            full_URL1 = new String(ModelURL.toString());
            
            
            AsyncDownloader modelDownloader = new AsyncDownloader(); 
            modelDownloader.execute();
            
            while(falloutbool != true)
            {
               // wait on background thread
            }
            
            String[] ModelLists = new String[SpinnerList2.size()];
            ModelLists = SpinnerList2.toArray(ModelLists);
            
            ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item
                    , ModelLists);
            // Specify the layout to use when the list of choices appears
            modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            modelAdapter.notifyDataSetChanged();
            // Apply the adapter to the spinner
            model_spinner.setAdapter(modelAdapter); 
            model_spinner.setSelection(0, true);
            falloutbool = false;
            
            break;
        case R.id.spinnerModel:
            
        
            break;
        default: break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
        
    }
    private class AsyncDownloader extends AsyncTask<Object,String,Integer> {

        @Override
        protected Integer doInBackground(Object... params) {
            Log.i(TAG, "In doInBackground task");

            XmlPullParser downloadData = tryDownloadingXmlData();
            int recordsFound = tryParsingXmlData(downloadData);
            return null;
        }

        private XmlPullParser tryDownloadingXmlData() {
            Log.i(TAG, "Trying to download XML");
            
            try {
                FedGov = new URL(full_URL1);
                XmlPullParser downloadData;
                downloadData= XmlPullParserFactory.newInstance().newPullParser();
                InputStream IS = FedGov.openConnection().getInputStream();
                downloadData.setInput(IS, null);
                return downloadData;
            } catch (XmlPullParserException e) {
                Log.i(TAG, "XML Pull Parser Exception");
            } catch (IOException e){
                Log.i(TAG, "IOException +");
                
            }
            
            return null;
        }
        private int tryParsingXmlData(XmlPullParser downloadData) {
            Log.i(TAG, "Trying to parse Data");

            if (downloadData != null){
                try {
                    return processDownloadData(downloadData);
                } catch (XmlPullParserException e) {
                    Log.i(TAG, "XmlPullParserException");
                } catch (IOException e){
                    Log.i(TAG, "IOException +");

                }
            } else {
                Log.i(TAG, "No Downloaded Data");

            }
            return 0;
        }

        private int processDownloadData(XmlPullParser xmlData) throws XmlPullParserException, IOException {
            Log.i(TAG, "Attempting Process");
            int recordsFound = 0; // Find values in the XML records
             int eventType = xmlData.getEventType();
             SpinnerList1.clear();SpinnerList2.clear();
             while (eventType != XmlPullParser.END_DOCUMENT) {
              if(eventType == XmlPullParser.START_DOCUMENT) {
                  recordsFound++;
                  Log.i(TAG,"Start document");
              } else if(eventType == XmlPullParser.START_TAG) {
                  recordsFound++;
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  if (xmlData.getName().equals("value")) {
                      SpinnerList1.add(xmlData.nextText().toString());
                  }
                  Log.i(TAG,"Start tag combo S"+xmlData.getName());
              } else if(eventType == XmlPullParser.END_TAG) {
                 Log.i(TAG,"End tag "+xmlData.getName());            
              } else if(eventType == XmlPullParser.TEXT) {
                  recordsFound++;
//                SpinnerLists.add(xmlData.getText());  
              }
              eventType = xmlData.next();
             }
             falloutbool = true;
             if (recordsFound == 0) {
                 publishProgress();
             }
             Log.i(TAG, "Finished processing "+recordsFound+" records.");
             return recordsFound;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values.length == 0)
                falloutbool = false;
                Log.i(TAG, "No Data Downloaded");
            if(values.length > 0) {
                falloutbool = true;
            }
            super.onProgressUpdate(values);
        }

        
    }
    private class AsyncDownloader1 extends AsyncTask<Object,String,Integer> {

        @Override
        protected Integer doInBackground(Object... params) {
            Log.i(TAG, "In doInBackground task");

            XmlPullParser downloadData = tryDownloadingXmlData();
            int recordsFound = tryParsingXmlData(downloadData);
            return null;
        }

        private XmlPullParser tryDownloadingXmlData() {
            Log.i(TAG, "Trying to download XML");
            
            try {
                FedGov = new URL(full_URL2);
                XmlPullParser downloadData;
                downloadData= XmlPullParserFactory.newInstance().newPullParser();
                InputStream IS = FedGov.openConnection().getInputStream();
                downloadData.setInput(IS, null);
                return downloadData;
            } catch (XmlPullParserException e) {
                Log.i(TAG, "XML Pull Parser Exception");
            } catch (IOException e){
                Log.i(TAG, "IOException +");
                
            }
            
            return null;
        }
        private int tryParsingXmlData(XmlPullParser downloadData) {
            Log.i(TAG, "Trying to parse Data");

            if (downloadData != null){
                try {
                    return processDownloadData(downloadData);
                } catch (XmlPullParserException e) {
                    Log.i(TAG, "XmlPullParserException");
                } catch (IOException e){
                    Log.i(TAG, "IOException +");

                }
            } else {
                Log.i(TAG, "No Downloaded Data");

            }
            return 0;
        }

        private int processDownloadData(XmlPullParser xmlData) throws XmlPullParserException, IOException {
            Log.i(TAG, "Attempting Process");
            int recordsFound = 0; // Find values in the XML records
             int eventType = xmlData.getEventType();
             SpinnerList2.clear();
             while (eventType != XmlPullParser.END_DOCUMENT) {
              if(eventType == XmlPullParser.START_DOCUMENT) {
                  recordsFound++;
                  Log.i(TAG,"Start document");
              } else if(eventType == XmlPullParser.START_TAG) {
                  recordsFound++;
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  if (xmlData.getName().equals("value")) {
                      SpinnerList2.add(xmlData.nextText().toString());
                  } else if (xmlData.getName().equals("value")) {
                      VehicleIDList.add(xmlData.nextText().toString());
                  }
                  Log.i(TAG,"Start tag combo S"+xmlData.getName());
              } else if(eventType == XmlPullParser.END_TAG) {
                 Log.i(TAG,"End tag "+xmlData.getName());            
              } else if(eventType == XmlPullParser.TEXT) {
                  recordsFound++;
//                SpinnerLists.add(xmlData.getText());  
              }
              eventType = xmlData.next();
             }
             if (recordsFound == 0) {
                 publishProgress();
             }
             Log.i(TAG, "Finished processing "+recordsFound+" records.");
             return recordsFound;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values.length == 0)
                falloutbool = false;
                Log.i(TAG, "No Data Downloaded");
            if(values.length > 0) {
                falloutbool = true;
            }
            super.onProgressUpdate(values);
        }

        
    }   
    private class AsyncDownloader2 extends AsyncTask<Object,String,Integer> {


        @Override
        protected Integer doInBackground(Object... params) {
            Log.i(TAG, "In doInBackground task");

            XmlPullParser downloadData = tryDownloadingXmlData();
            int recordsFound = tryParsingXmlData(downloadData);
            return null;
        }

        private XmlPullParser tryDownloadingXmlData() {
            Log.i(TAG, "Trying to download XML");
            
            try {
                FedGov = new URL(full_URL3);
                XmlPullParser downloadData;
                downloadData= XmlPullParserFactory.newInstance().newPullParser();
                InputStream IS = FedGov.openConnection().getInputStream();
                downloadData.setInput(IS, null);
                return downloadData;
            } catch (XmlPullParserException e) {
                Log.i(TAG, "XML Pull Parser Exception");
            } catch (IOException e){
                Log.i(TAG, "IOException +");
                
            }
            
            return null;
        }
        private int tryParsingXmlData(XmlPullParser downloadData) {
            Log.i(TAG, "Trying to parse Data");

            if (downloadData != null){
                try {
                    return processDownloadData(downloadData);
                } catch (XmlPullParserException e) {
                    Log.i(TAG, "XmlPullParserException");
                } catch (IOException e){
                    Log.i(TAG, "IOException +");

                }
            } else {
                Log.i(TAG, "No Downloaded Data");

            }
            return 0;
        }

        private int processDownloadData(XmlPullParser xmlData) throws XmlPullParserException, IOException {
            Log.i(TAG, "Attempting Process");
            int recordsFound = 0; // Find values in the XML records
             int eventType = xmlData.getEventType();
             SpinnerList3.clear();
             VehicleIDList.clear();
             while (eventType != XmlPullParser.END_DOCUMENT) {
              if(eventType == XmlPullParser.START_DOCUMENT) {
                  recordsFound++;
                  Log.i(TAG,"Start document");
              } else if(eventType == XmlPullParser.START_TAG) {
                  recordsFound++;
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  if (xmlData.getName().equals("text")) {
                      SpinnerList3.add(xmlData.nextText().toString());
                  } else if (xmlData.getName().equals("value")) {
                      VehicleIDList.add(xmlData.nextText().toString());
                  }
                  
                  Log.i(TAG,"Start tag combo S"+xmlData.getName());
              } else if(eventType == XmlPullParser.END_TAG) {
                 Log.i(TAG,"End tag "+xmlData.getName());            
              } else if(eventType == XmlPullParser.TEXT) {
                  recordsFound++;
//                SpinnerLists.add(xmlData.getText());  
              }
              eventType = xmlData.next();
             }
             falloutbool = true;
             if (recordsFound == 0) {
                 publishProgress();
             }
             Log.i(TAG, "Finished processing "+recordsFound+" records.");
             return recordsFound;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values.length == 0)
                falloutbool = false;
                Log.i(TAG, "No Data Downloaded");
            if(values.length > 0) {
                falloutbool = true;
            }
            super.onProgressUpdate(values);
        }       
    }
    private class AsyncDownloader3 extends AsyncTask<Object,String,Integer> {
        @Override
        protected Integer doInBackground(Object... params) {
            Log.i(TAG, "In doInBackground task");

            XmlPullParser downloadData = tryDownloadingXmlData();
            int recordsFound = tryParsingXmlData(downloadData);
            return null;
        }

        private XmlPullParser tryDownloadingXmlData() {
            Log.i(TAG, "Trying to download XML");
            
            try {
                FedGov = new URL(full_URL4);
                XmlPullParser downloadData;
                downloadData= XmlPullParserFactory.newInstance().newPullParser();
                InputStream IS = FedGov.openConnection().getInputStream();
                downloadData.setInput(IS, null);
                return downloadData;
            } catch (XmlPullParserException e) {
                Log.i(TAG, "XML Pull Parser Exception");
            } catch (IOException e){
                Log.i(TAG, "IOException +");
                
            }
            
            return null;
        }
        private int tryParsingXmlData(XmlPullParser downloadData) {
            Log.i(TAG, "Trying to parse Data");

            if (downloadData != null){
                try {
                    return processDownloadData(downloadData);
                } catch (XmlPullParserException e) {
                    Log.i(TAG, "XmlPullParserException");
                } catch (IOException e){
                    Log.i(TAG, "IOException +");

                }
            } else {
                Log.i(TAG, "No Downloaded Data");

            }
            return 0;
        }

        private int processDownloadData(XmlPullParser xmlData) throws XmlPullParserException, IOException {
            Log.i(TAG, "Attempting Process");
            int recordsFound = 0; // Find values in the XML records
             int eventType = xmlData.getEventType();
             while (eventType != XmlPullParser.END_DOCUMENT) {
              if(eventType == XmlPullParser.START_DOCUMENT) {
                  recordsFound++;
                  Log.i(TAG,"Start document");
              } else if(eventType == XmlPullParser.START_TAG) {
                  recordsFound++;
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  Log.i(TAG,"Start tag combo F "+xmlData.getName());
                  if (xmlData.getName().equals("comb08")) {
                      MPG = xmlData.nextText().toString();
                  }               
                  Log.i(TAG,"Start tag combo S"+xmlData.getName());
              } else if(eventType == XmlPullParser.END_TAG) {
                 Log.i(TAG,"End tag "+xmlData.getName());            
              } else if(eventType == XmlPullParser.TEXT) {
                  recordsFound++;
//                SpinnerLists.add(xmlData.getText());  
              }
              eventType = xmlData.next();
             }
             falloutbool = true;
             if (recordsFound == 0) {
                 publishProgress();
             }
             Log.i(TAG, "Finished processing "+recordsFound+" records.");
             return recordsFound;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values.length == 0)
                falloutbool = false;
                Log.i(TAG, "No Data Downloaded");
            if(values.length > 0) {
                falloutbool = true;
            }
            super.onProgressUpdate(values);
        }

        
    }
}