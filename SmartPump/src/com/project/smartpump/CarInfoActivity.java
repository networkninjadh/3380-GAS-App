/**
 * @author Damond Howard
 */
package com.project.smartpump;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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

import com.project.classes.DataParser;
import com.project.classes.DatabaseAccess;
import com.project.classes.Vehicle;

public class CarInfoActivity extends Activity implements OnItemSelectedListener 
{	
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
	private Vehicle vehicle = new Vehicle();
	private static URL FedGov; 
	private static Boolean falloutbool = false;
	private boolean firstRunCheckYearSpinner = true;
	private boolean firstRunCheckMakeSpinner = true;
	private boolean firstRunCheckModelSpinner = true;
	private boolean firstRunCheckOptionSpinner = true; 
	
	Context context = this;
	Button AddVehicle,Reset;
	Spinner year_spinner, make_spinner, model_spinner,options_spinner;
	String make,model,year;
	int vID;
	boolean profileWasMade = false;
	
	private static ArrayAdapter<CharSequence> adapter = null, makeAdapter = null,modelAdapter = null, optionsAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	super.onCreate(savedInstanceState);
	
		
		SharedPreferences sharedPref = this.getSharedPreferences(
		        getString(R.string.SmartPumpMPGFile), Context.MODE_PRIVATE);
	
	
		setContentView(R.layout.car_info);
		AddVehicle 	 = (Button)findViewById(R.id.button1);
		Reset 	     = (Button)findViewById(R.id.button2);
		year_spinner = (Spinner)findViewById(R.id.spinnerYear);	
		make_spinner = (Spinner)findViewById(R.id.spinnerMake);	
		model_spinner = (Spinner)findViewById(R.id.spinnerModel);
		options_spinner = (Spinner)findViewById(R.id.spinnerOptions);
		
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
		
    	year_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (firstRunCheckYearSpinner == false){
					year = year_spinner.getSelectedItem().toString();
					Log.i(TAG, "Query Database...");
					
					StringBuilder RequestURL = new StringBuilder(SERVER_URL);
					RequestURL.append("make?year=" + year_spinner.getSelectedItem());
					full_URL1 = RequestURL.toString();
	
					AsyncDownloader downloader = new AsyncDownloader(); 
					downloader.execute();
	
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
				}
				firstRunCheckYearSpinner = false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
    	});
    	make_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (firstRunCheckMakeSpinner == false){
					make = make_spinner.getSelectedItem().toString();
					Log.i(TAG, "Query Database 2...");
					
					StringBuilder ModelURL = new StringBuilder(SERVER_URL);
					ModelURL.append("model?year=" + year + "&make=" + make);
					full_URL2 = new String(ModelURL.toString());
					
					AsyncDownloader1 modelDownloader = new AsyncDownloader1(); 
					modelDownloader.execute();
					android.os.SystemClock.sleep(1000);
					
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
					model_spinner.setSelection(-1, false);
					android.os.SystemClock.sleep(1000);
			    	modelAdapter.notifyDataSetChanged();
				}
				firstRunCheckMakeSpinner = false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
    	});
    	model_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (firstRunCheckModelSpinner == false){	
					model = model_spinner.getSelectedItem().toString();
					Log.i(TAG, "Query Database 3...");
					
					StringBuilder ModelURL = new StringBuilder(SERVER_URL);
					ModelURL.append("options?year=" + year + "&make=" + make + "&model=" + model);
					full_URL3 = new String(ModelURL.toString());
					
					AsyncDownloader2 optionsDownloader = new AsyncDownloader2(); 
					optionsDownloader.execute();
					android.os.SystemClock.sleep(1000);
					
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
					
					options_spinner.setSelection(-1, false);
					android.os.SystemClock.sleep(2000);
			    	optionsAdapter.notifyDataSetChanged();
				}	
				firstRunCheckModelSpinner = false;
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
    	});
    	options_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(firstRunCheckOptionSpinner == false) {
					
					vID = model_spinner.getSelectedItemPosition();
					full_URL4 = "http://www.fueleconomy.gov/ws/rest/vehicle/" + vID;
					AsyncDownloader3 MPGDownloader = new AsyncDownloader3();
					MPGDownloader.execute();
				}
				firstRunCheckOptionSpinner = false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
		AddVehicle.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				vehicle.setVehicleID(VehicleIDList.get(vID).toString());
				Intent MainActivity = new Intent(CarInfoActivity.this, MainActivityTest	.class);
				startActivity(MainActivity);
			}	
		
		});
		Reset.setOnClickListener(new OnClickListener()
		{	@Override
			public void onClick(View v) 
			{	
				reset();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{	getMenuInflater().inflate(R.menu.car_info, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{	switch (item.getItemId())
		{	case R.id.action_settings:
				Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
			break;
			case R.id.action_done:		
				if (profileWasMade) //if a profile exists or a new one was made
				{	Intent intent = new Intent(context,MapView.class);
					startActivity(intent);
					finish();
				}
				else //do this when nothing was pressed error in here
				{	String message = "It seems that no vehicle profiles exist if you wish to add" +
							"one later just use the settings menu";
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage(message)
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() 
						{	@Override
							public void onClick(DialogInterface dialog, int which) 
							{	Intent intent = new Intent(context,MapView.class);
								startActivity(intent);
								finish();
							}
						});
					AlertDialog alert = builder.create();
					alert.show();
				}
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
	public void AddVehicle(int vehicleYear,String vehicleMake, String vehicleModel)
	{	String profileName;
		final EditText txtProfileName = new EditText(this);
		String message = "Please enter a profile name for your vehicle.";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
			.setCancelable(false)
			.setView(txtProfileName)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() 
			{	@Override
				public void onClick(DialogInterface dialog, int which) 
				{	
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
		profileName = txtProfileName.getText().toString();
		profileWasMade = true;
		if (profileName != "")
			reset();
		else
			alert.show();
	}
	/**
	 *takes the vehicle information and passes it to the database
	 * @param vehicleID
	 */
	public void reset()

	{	
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
//	        	  SpinnerLists.add(xmlData.getText());  
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
//	        	  SpinnerLists.add(xmlData.getText());  
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
//	        	  SpinnerLists.add(xmlData.getText());  
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
	        	  if (xmlData.getName().equals("comb08U")) {
	        		  SpinnerList3.add(xmlData.nextText().toString());
	        		  
	        	  }	        	  
	        	  Log.i(TAG,"Start tag combo S"+xmlData.getName());
	          } else if(eventType == XmlPullParser.END_TAG) {
	        	 Log.i(TAG,"End tag "+xmlData.getName());	         
	          } else if(eventType == XmlPullParser.TEXT) {
	        	  recordsFound++;
//	        	  SpinnerLists.add(xmlData.getText());  
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

