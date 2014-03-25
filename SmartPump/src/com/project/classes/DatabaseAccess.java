/**
 * @author Damond Howard
 * Database functions to be filled in that interface with external car data
 */
package com.project.classes;

import com.project.smartpump.MainActivity;
import com.project.smartpump.ProfileDatabaseHelper;

//class used to interface with external databases 
public class DatabaseAccess 
{	public static void lookupCarByID(String vehicleID) 
	{	// TODO Auto-generated method stub	
	}
	public static void saveCarToMemory(int vehicleYear, String vehicleMake, String vehicleModel, String vehicleID, String profileName) 
	{	// TODO Auto-generated method stub	
		ProfileDatabaseHelper mDbHelper = new ProfileDatabaseHelper(MainActivity.getContext());
		
	}
}
