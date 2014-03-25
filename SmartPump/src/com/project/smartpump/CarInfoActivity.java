/**
 * @author Damond Howard
 */
package com.project.smartpump;

import com.project.classes.DatabaseAccess;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CarInfoActivity extends Activity 
{	Context context = getBaseContext();
	Button AddVehicle,Reset;
	EditText VehicleYear, VehicleMake, VehicleModel, VehicleID;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.car_info);
		AddVehicle = (Button)findViewById(R.id.button1);
		Reset = (Button)findViewById(R.id.button2);
		VehicleYear = (EditText)findViewById(R.id.editText1);
		VehicleMake = (EditText)findViewById(R.id.editText2);
		VehicleModel = (EditText)findViewById(R.id.editText3);
		VehicleID = (EditText)findViewById(R.id.editText4);
		AddVehicle.setOnClickListener(new OnClickListener()
		{	@Override
			public void onClick(View v) 
			{	boolean allValuesEntered = false;
				while (allValuesEntered == false)
				{
					if (VehicleYear.getText().toString().trim().equals(""))
					{	//tell the user to enter year
						Toast.makeText(CarInfoActivity.this, "It seems like you haven't entered the year", Toast.LENGTH_SHORT).show();
						allValuesEntered = false;
					}
					if (VehicleMake.getText().toString().trim().equals(""))
					{	//tell the user to enter make
						Toast.makeText(CarInfoActivity.this, "It seems like you haven't entered the make", Toast.LENGTH_LONG).show();
						allValuesEntered = false;
					}
					if (VehicleModel.getText().toString().trim().equals(""))
					{	//tell the user to enter the model
						Toast.makeText(CarInfoActivity.this, "It seems like you haven't entered the model", Toast.LENGTH_LONG).show();
						allValuesEntered = false;
					}
					if (!VehicleYear.getText().toString().trim().equals("") && !VehicleMake.getText().toString().trim().equals("") && !VehicleModel.getText().toString().trim().equals(""))
					{
						allValuesEntered = true;
					}
				}
				AddVehicle(Integer.parseInt(VehicleYear.getText().toString()),VehicleMake.getText().toString(),
						VehicleModel.getText().toString(),VehicleID.getText().toString());
			}
		});
		Reset.setOnClickListener(new OnClickListener()
		{	@Override
			public void onClick(View v) 
			{	reset();
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
			default:
			break;
		}
		return true;
	}
	/**
	 * 
	 * @param vehicleYear
	 * @param vehicleMake
	 * @param vehicleModel
	 * @param VehicleID
	 */
	public void AddVehicle(int vehicleYear,String vehicleMake, String vehicleModel, String VehicleID)
	{	String profileName;
		//add the user's car to the database
		//ask user to enter a name for the cars profile
		final EditText txtProfileName = new EditText(this);
		String message = "Please enter a profile name for your car.";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
			.setCancelable(false)
			.setView(txtProfileName)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() 
			{	@Override
				public void onClick(DialogInterface dialog, int which) 
				{	//what to do when button is pressed	
				}
			});
		AlertDialog alert = builder.create();
		do{
			alert.show();
			profileName = txtProfileName.getText().toString();
			DatabaseAccess.saveCarToMemory(vehicleYear, vehicleMake, vehicleModel, VehicleID, profileName);
			reset();
		} while(true);
		//else loop through the code to add another car to the database
	}
	public void reset()
	{	VehicleYear.setText("");
		VehicleModel.setText("");
		VehicleMake.setText("");
	}
}