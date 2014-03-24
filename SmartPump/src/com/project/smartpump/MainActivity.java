package com.project.smartpump;

import android.os.Bundle;
import android.app.Activity;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
				{
					//getInfoFromDatabase();	
				}
				else
				{
					Double out = calculate(Double.parseDouble(MPG.getText().toString()),Double.parseDouble(FuelPrice.getText().toString()),
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
	{
		switch (item.getItemId())
		{
			//action bar item defined in XML
			case R.id.action_settings:
				Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
				//go to settings activity
				break;
				default:
				break;
		}
		return true;
	}
	public static double calculate(double MPG, double stationCost, double distanceToStation, double numberOfGallonsPlanned)
	{	double realCost;
		double distanceInGals;
        double additionalCostInDollars;
        distanceInGals = (distanceToStation/MPG);
        additionalCostInDollars = distanceInGals*stationCost;
        realCost = ((stationCost * numberOfGallonsPlanned) + additionalCostInDollars)/numberOfGallonsPlanned;
        return realCost;
	}
	public static double calculate(double MPG,double FuelPrice, double Miles)
	{	double realCost = 0.0;
		return realCost;
	}
	public static void reset()
	{	MPG.setText("");
		FuelPrice.setText("");
		Miles.setText("");
		output.setText("");
	}
	public static void getInfoFromDatabase()
	{	//gets fuelPrice MPG and Miles from externel database
	}
}