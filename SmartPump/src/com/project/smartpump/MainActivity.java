/**
 * @author Damond Howard
 */

package com.project.smartpump;

import com.project.classes.Calculations;
//import com.project.classes.DatabaseAccess;
//import com.project.classes.Vehicle;

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
	{	return context;
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
			{	if (FuelPrice.getText().toString().trim().equals("") && MPG.getText().toString().trim().equals("") && Miles.getText().toString().trim().equals(""))
				{	//Vehicle vehicle = DatabaseAccess.getInfoFromDatabase();	
				}
				else
				{	Double out = Calculations.calculate(Double.parseDouble(MPG.getText().toString()),Double.parseDouble(FuelPrice.getText().toString()),
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
		{	case R.id.action_settings:
				Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
				break;
				default:
				break;		
		}
		return true;
	}
	/**
	 * resets the fields 
	 */
	public void reset()
	{	MPG.setText("");
		FuelPrice.setText("");
		Miles.setText("");
		output.setText("");
	}
}