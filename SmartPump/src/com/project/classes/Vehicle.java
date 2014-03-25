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
	
	Vehicle(int vehicleYear, String vehicleMake, String vehicleModel, String vehicleID)
	{
		this.vehicleID = vehicleID;
		this.vehicleMake = vehicleMake;
		this.vehicleModel = vehicleModel;
		this.vehicleYear = vehicleYear;
	}
	int getVehicleYear()
	{
		return this.vehicleYear;
	}
	String getVehicleMake()
	{
		return this.vehicleMake;
	}
	String getVehicleModel()
	{
		return this.vehicleModel;
	}
	String getVehicleID()
	{
		return this.vehicleID;
	}
}
