package com.project.smartpump;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity 
{	EditText FuelPrice,MPG,Miles;
	Button   calculate,reset;
	TextView output;
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
					{	calculate();
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
	public static int calculate()
	{
		
	}
	public static void reset()
	{
		
	}
}
