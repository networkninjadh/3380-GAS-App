/**
 * @author Damond Howard
 */
package com.project.smartpump;

import android.provider.BaseColumns;

public class ProfileDatabaseContract 
{	/**
	 * empty constructor so noone creates an instance of this class
	 */
	public ProfileDatabaseContract(){}
	/**
	 * returns the sql query to make database entry
	 * @return the sqlCreateEntries
	 */
	public static String getSqlCreateEntries() 
	{	return SQL_CREATE_ENTRIES;
	}
	/**
	 * returns the sql query to delete database entry
	 * @return the sqlDeleteEntries
	 */
	public static String getSqlDeleteEntries()
	{	return SQL_DELETE_ENTRIES;
	}
	/* Inner class that defines the table contents */
	public static abstract class ProfileEntry implements BaseColumns
	{	public static final String TABLE_NAME = "entry";
		public static final String COLUMN_NAME_PROFILE_NAME = "profile";
		public static final String COLUMN_NAME_MAKE = "make";
		public static final String COLUMN_NAME_MODEL = "model";
		public static final String COLUMN_NAME_YEAR = "year";
	}
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA = ",";
	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + ProfileEntry.TABLE_NAME + " (" +
			ProfileEntry._ID + " INTEGER PRIMARY KEY," + 
			ProfileEntry.COLUMN_NAME_PROFILE_NAME + TEXT_TYPE + COMMA +
			ProfileEntry.COLUMN_NAME_MAKE 	+ TEXT_TYPE + COMMA +
			ProfileEntry.COLUMN_NAME_MODEL 	+ TEXT_TYPE + COMMA +
			ProfileEntry.COLUMN_NAME_YEAR 	+ TEXT_TYPE + COMMA +
			" )";
	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + ProfileEntry.TABLE_NAME;
}