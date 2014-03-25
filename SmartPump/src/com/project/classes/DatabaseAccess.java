/**
 * @author Damond Howard
 * Database functions to be filled in that interface with external car data
 */
package com.project.classes;

//import com.project.smartpump.MainActivity;
//import com.project.smartpump.ProfileDatabaseHelper;

//class used to interface with external databases 
public class DatabaseAccess 
{	public static void lookupCarByID(String vehicleID) 
	{	//find the car by it's id
	}
	public static void findID(int vehicleYear, String vehicleMake, String vehicleModel)
	{
		//find the vehicleID based of make model and year
	}
	public static void saveCarToMemory(int vehicleYear, String vehicleMake, String vehicleModel, String vehicleID, String profileName) 
	{	// use databasehelper to read and write from database
		//ProfileDatabaseHelper mDbHelper = new ProfileDatabaseHelper(MainActivity.getContext());
	}
}
