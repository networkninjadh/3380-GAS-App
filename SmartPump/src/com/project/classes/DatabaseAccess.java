/**
 * @author Damond Howard
 * Database functions to be filled in that interface with external car data
 */
package com.project.classes;

//import com.project.smartpump.MainActivity;
//import com.project.smartpump.ProfileDatabaseHelper;

//class used to interface with external databases 
public class DatabaseAccess 
{	/**
	 * sees if a car is in the database based off its vehicleID
	 * @param vehicleID
	 * @return whether the car is in the database
	 */
	public static boolean lookupCarByID(String vehicleID) 
	{	//find the car by it's id
		return false; //placeholder
	}
	/**
	 * 
	 * @param vehicleYear
	 * @param vehicleMake
	 * @param vehicleModel
	 * @return vehicleID String
	 */
	public static String findID(int vehicleYear, String vehicleMake, String vehicleModel)
	{	//find the vehicleID based of make model and year
		return null;
	}
	/**
	 * attempts to save a car to the internal database returns false if failure
	 * @param vehicleYear
	 * @param vehicleMake
	 * @param vehicleModel
	 * @param vehicleID
	 * @param profileName
	 * @return whether database transaction was successful 
	 */
	public static boolean saveCarToMemory(int vehicleYear, String vehicleMake, String vehicleModel, String vehicleID, String profileName) 
	{	// use databasehelper to read and write from database
		//ProfileDatabaseHelper mDbHelper = new ProfileDatabaseHelper(MainActivity.getContext());
		return false;
	}
	/**
	 * Returns a Vehicle object representing the last car added to the database
	 * @return the last car added to the database
	 */
	public static Vehicle getInfoFromDatabase()
	{	return null;
	}
	/**
	 * Returns a Vehicle object representing car with vehicleID
	 * @param vehicleID
	 * @return Vehicle
	 */
	public static Vehicle getInfoFromDatabase(String vehicleID) 
	{	return null;
	}
	/**
	 * Returns Vehicle object representing car with following info
	 * @param vehicleYear
	 * @param vehicleMake
	 * @param vehicleModel
	 * @return
	 */
	public static Vehicle getInfoFromDatabase(String vehicleYear, String vehicleMake, String vehicleModel)
	{	return null;
	}
}
