package com.project.smartpump;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CarInfoActivity extends Activity 
{	Button AddVehicle,Reset;
	EditText VehicleYear, VehicleMake, VehicleModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.car_info);
		AddVehicle = (Button)findViewById(R.id.button1);
		Reset = (Button)findViewById(R.id.button2);
		VehicleYear = (EditText)findViewById(R.id.editText1);
		VehicleMake = (EditText)findViewById(R.id.editText2);
		VehicleModel = (EditText)findViewById(R.id.editText3);
		AddVehicle.setOnClickListener(new OnClickListener()
		{	@Override
			public void onClick(View v) 
			{	AddVehicle(Integer.parseInt(VehicleYear.getText().toString()),VehicleMake.getText().toString(),VehicleModel.getText().toString());
			}
		});
		Reset.setOnClickListener(new OnClickListener()
		{	@Override
			public void onClick(View v) 
			{	Reset();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{	getMenuInflater().inflate(R.menu.car_info, menu);
		return true;
	}
	public static void AddVehicle(int year,String make, String model)
	{
		//add the user's car to the database
	}
	public static void Reset()
	{
		//reset the form data
	}
}