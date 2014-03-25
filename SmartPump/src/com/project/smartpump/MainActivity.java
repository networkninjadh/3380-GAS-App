/**
 * @author Damond Howard
 */

package com.project.smartpump;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
//import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
  
public class MainActivity extends Activity 
{	static EditText FuelPrice,MPG,Miles;
	static Button   calculate,reset;
	static TextView output;
	public static Context context;
	public static Context getContext()
	{
		return context;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		FuelPrice = (EditText)findViewById(R.id.editText1);
		MPG = (EditText)findViewById(R.id.editText2);
		Miles = (EditText)findViewById(R.id.editText3);
		calculate = (Button)findViewById(R.id.button1);
		reset = (Button)findViewById(R.id.button2);
		output = (TextView)findViewById(R.id.textView1);
		calculate.setOnClickListener(new OnClickListener()
		{	@Override
			public void onClick(View v) 
			{	if (FuelPrice.getText().toString() == "" && MPG.getText().toString() == "" && Miles.getText().toString() == "")
				{	//getInfoFromDatabase();	
				}
				else
				{	Double out = calculate(Double.parseDouble(MPG.getText().toString()),Double.parseDouble(FuelPrice.getText().toString()),
								Double.parseDouble(Miles.getText().toString()),1.0);
					output.setText(out.toString());
				}				
			}
		});
		reset.setOnClickListener(new OnClickListener()
		{	@Override
			public void onClick(View v) 
			{	reset();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{	switch (item.getItemId())
		{	//action bar item defined in XML
			case R.id.action_settings:
				Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
				//go to settings activity
				break;
				default:
				break;		
		}
		return true;
	}
	public double calculate(double MPG, double stationCost, double distanceToStation, double numberOfGallonsPlanned)
	{	double realCost;
		double distanceInGals;
        double additionalCostInDollars;
        distanceInGals = (distanceToStation/MPG);
        additionalCostInDollars = distanceInGals*stationCost;
        realCost = ((stationCost * numberOfGallonsPlanned) + additionalCostInDollars)/numberOfGallonsPlanned;
        return realCost;
	}
	public double calculate(double MPG,double stationCost, double Miles)
	{	double realCost;
		double distanceInGals;
		double additionalCostInDollars;
		distanceInGals = (1.0/MPG);
		additionalCostInDollars = distanceInGals*stationCost;
		realCost = ((stationCost * 1.0) + additionalCostInDollars)/1.0;
		return realCost;
	}
	public void reset()
	{	MPG.setText("");
		FuelPrice.setText("");
		Miles.setText("");
		output.setText("");
	}
	public void getInfoFromDatabase()
	{	//gets fuelPrice MPG and Miles from externel database
	}
}