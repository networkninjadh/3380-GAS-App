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
	public int getVehicleYear()
	{
		return this.vehicleYear;
	}
	/**
	 * 
	 * @param year
	 */
	public void setVehicleYear(int year)
	{
		this.vehicleYear = year;
	}
	/**
	 * @return String
	 */
	public String getVehicleMake()
	{
		return this.vehicleMake;
	}
	/**
	 * 
	 * @param make
	 */
	public void setVehicleMake(String make)
	{
		this.vehicleMake = make;
	}
	/**
	 * @return String
	 */
	public String getVehicleModel()
	{
		return this.vehicleModel;
	}
	/**
	 * 
	 * @param model
	 */
	public void setVehicleModel(String model)
	{
		this.vehicleModel = model;
	}
	/**
	 * @return String
	 */
	public String getVehicleID()
	{
		return this.vehicleID;
	}
	/**
	 * 
	 * @param id
	 */
	public void setVehicleID(String id)
	{
		this.vehicleID = id;
	}
	/**
	 * @return String
	 */
	public String getVehicleProfileName()
	{
		return this.vehicleProfileName;
	}
	/**
	 * 
	 * @param name
	 */
	public void setVehicleProfileName(String name)
	{
		this.vehicleProfileName = name;
	}
	
}
