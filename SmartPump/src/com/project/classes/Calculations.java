/**
 * all calculations should be kept in this class
 * @author Damond Howard
 */
package com.project.classes; 


public class Calculations
{	/**
	 * calculates cost based off mpg gas prices distance to the gas station and how many gallons the user plans to buy
	 * @param MPG
	 * @param stationCost
	 * @param distanceToStation
	 * @param numberOfGallonsPlanned
	 * @return realCost
	 */
	public static double calculate(double MPG, double stationCost, double distanceToStation, double numberOfGallonsPlanned)
	{	double realCost;
		double distanceInGals;
        double additionalCostInDollars;
        distanceInGals = (distanceToStation/MPG);
        additionalCostInDollars = distanceInGals*stationCost;
        realCost = ((stationCost * numberOfGallonsPlanned) + additionalCostInDollars)/numberOfGallonsPlanned;
        return realCost;
	}
	/**
	 * calculates cost based off miles mpg and gas price
	 * @param MPG
	 * @param stationCost
	 * @param Miles
	 * @return realCost
	 */
	public static double calculate(double MPG,double stationCost, double Miles)
	{	double realCost;
		double distanceInGals;
		double additionalCostInDollars;
		distanceInGals = (1.0/MPG);
		additionalCostInDollars = distanceInGals*stationCost;
		realCost = ((stationCost * 1.0) + additionalCostInDollars)/1.0;
		return realCost;
	}
}
