/**
 * @author Damond Howard
 */
package com.project.classes;

public class GasStation 
{	private double fulePrice;
	private String phoneNumber;
	private String webAdress;
	private String city;
	private String state;
	private int    zipCode;
	
	/**
	 * creates a gas station object which represents a particular gas station
	 * @param fuelPrice
	 * @param phoneNumber
	 * @param webAddress
	 * @param city
	 * @param state
	 * @param zipCode
	 */
	GasStation(double fuelPrice, String phoneNumber, String webAddress, 
		String city, String state, int zipCode)
	{	this.fulePrice = fuelPrice;
		this.phoneNumber = phoneNumber;
		this.webAdress = webAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	/**
	 * 
	 * @return the fuel price of this station
	 */
	public double getFuelPrice()
	{	return this.fulePrice;	
	}
	/**
	 * 
	 * @return the phone number of the gas station
	 */
	public String getPhoneNumber()
	{	return this.phoneNumber;
	}
	/**
	 * 
	 * @return the web site of this gas station
	 */
	public String getWebAddress()
	{	return this.webAdress;
	}
	/**
	 * 
	 * @return the city for this gas station
	 */
	public String getCity()
	{	return this.city;
	}
	/**
	 * 
	 * @return the state of the gas station
	 */
	public String getState()
	{	return this.state;
	}
	/**
	 * 
	 * @return the zip code of the gas station
	 */
	public int getZipCode()
	{	return this.zipCode;
	}
	
	@Override public String toString()
	{
	    StringBuilder result = new StringBuilder();
	    String nl = System.getProperty("line.separator");
	    
	    result.append("Address: " + getWebAddress() + nl);
	    result.append("City: " + getCity() + nl);
	    result.append("State: " + getState() + nl);
	    result.append("Zip: " + getZipCode() + nl);
	    result.append("Phone Number: " + getPhoneNumber() + nl);
	    result.append("Fuel Price: " + getFuelPrice() + nl);
	    result.append(nl);
	    return result.toString();
	}
}