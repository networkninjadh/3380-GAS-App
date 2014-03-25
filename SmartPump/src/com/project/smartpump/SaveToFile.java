/**
 * @Author Damond Howard
 */

//databasename = car_profiles
/*database fields 
 * profileName | vehicleID | vehicleMake | vehicleModel | vehicleYear
 */
package com.project.smartpump;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveToFile extends SQLiteOpenHelper
{	private static final int DATABASE_VERSION = 2;
	private static final String CAR_PROFILES_TABLE_NAME = "car_profiles";
	private static final String KEY_DEFINITION = null;
	private static final String KEY_WORD = null;
	private static final String CAR_PROFILES_TABLE_CREATE =
			"CREATE TABLE " + CAR_PROFILES_TABLE_NAME + " (" +
	         KEY_WORD + " TEXT, " +
	         KEY_DEFINITION + " TEXT);";
	private static final String DATABASE_NAME = null;

	SaveToFile(Context context) 
	{	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) 
	{	db.execSQL(CAR_PROFILES_TABLE_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{	
	}
}
