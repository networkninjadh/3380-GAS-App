/**
 * @author Damond Howard
 * Class that holds vehicle information
 */
package com.project.classes;

public class Vehicle 
{
	private String vehicleMake;
	private String vehicleModel;
	private String vehicleID;
	private int vehicleYear;
	private String vehicleProfileName; //ID to keep track of vehicle profile
	
	/**
	 * 
	 * @param vehicleYear
	 * @param vehicleMake
	 * @param vehicleModel
	 * @param vehicleID
	 * @param vehicleProfileName
	 */
	Vehicle(int vehicleYear, String vehicleMake, String vehicleModel, String vehicleID, String vehicleProfileName)
	{
		this.vehicleID = vehicleID;
		this.vehicleMake = vehicleMake;
		this.vehicleModel = vehicleModel;
		this.vehicleYear = vehicleYear;
		this.vehicleProfileName = vehicleProfileName;
	}
	/**
	 * @return int
	 */
	int getVehicleYear()
	{
		return this.vehicleYear;
	}
	/**
	 * @return String
	 */
	String getVehicleMake()
	{
		return this.vehicleMake;
	}
	/**
	 * @return String
	 */
	String getVehicleModel()
	{
		return this.vehicleModel;
	}
	/**
	 * @return String
	 */
	String getVehicleID()
	{
		return this.vehicleID;
	}
	/**
	 * @return String
	 */
	String getVehicleProfileName()
	{
		return this.vehicleProfileName;
	}
}
