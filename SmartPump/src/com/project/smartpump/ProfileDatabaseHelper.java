/**
d * @Author Damond Howard
 */

//databasename = car_profiles
/*database fields 
 * profileName | vehicleID | vehicleMake | vehicleModel | vehicleYear
 */
package com.project.smartpump;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDatabaseHelper extends SQLiteOpenHelper
{	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "ProfileEntry.db";
	/**
	 * 
	 * @param context
	 */
	public ProfileDatabaseHelper(Context context) 
	{	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) 
	{	db.execSQL(ProfileDatabaseContract.getSqlCreateEntries());
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{	
		db.execSQL(ProfileDatabaseContract.getSqlDeleteEntries());
		onCreate(db);
	}
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
    	onUpgrade(db,oldVersion, newVersion);
    }
}
