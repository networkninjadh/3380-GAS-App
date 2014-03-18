//used to hold the navigation map
package com.project.smartpump;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MapActivity extends Activity
{	Button button1;
	TextView latitude, longitude;
	Location loc;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		latitude = (TextView)findViewById(R.id.textView3);
		longitude = (TextView)findViewById(R.id.textView4);
		latitude.setText(((Double)loc.getLatitude()).toString());
		longitude.setText(((Double)loc.getLongitude()).toString());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{	getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
}