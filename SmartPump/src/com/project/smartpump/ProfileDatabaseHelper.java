/**
 * @Author Damond Howard
 */

//databasename = car_profiles
/*database fields 
 * | profileName | vehicleID | vehicleMake | vehicleModel | vehicleYear |
 */
package com.project.smartpump;

import com.project.classes.Vehicle;

import android.content.ContentValues;
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
	{	db.execSQL(ProfileDatabaseContract.getSqlDeleteEntries());
		onCreate(db);
	}
    @Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {	onUpgrade(db,oldVersion, newVersion);
    }
    public void addVehicle(Vehicle vehicle)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(ProfileDatabaseContract.ProfileEntry.COLUMN_NAME_PROFILE_NAME,vehicle.getVehicleProfileName());
    	values.put(ProfileDatabaseContract.ProfileEntry.COLUMN_NAME_MAKE,vehicle.getVehicleMake());
    	values.put(ProfileDatabaseContract.ProfileEntry.COLUMN_NAME_MODEL, vehicle.getVehicleModel());
    	values.put(ProfileDatabaseContract.ProfileEntry.COLUMN_NAME_YEAR, vehicle.getVehicleYear());
    	db.insert(ProfileDatabaseContract.ProfileEntry.TABLE_NAME,null,values);
    	db.close();
    }
    public Vehicle getVehicle(int id)
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    }
}
