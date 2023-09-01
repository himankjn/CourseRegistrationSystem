package com.wibmo.constants;

/**
 * 
 * @author shanmukh
 * Enumeration class for GenderConstant
 *
 */
public enum GenderConstant {
	MALE(0),FEMALE(1),OTHER(2);
	
	private int gender;
	
	/**
	 * Parameterized Constructor
	 * @param gender
	 */
	private GenderConstant(int gender)
	{
		this.gender=gender;
	}
	
	/**
	 * Method to return gender type in String
	 * @return GenderConstant name in String
	 */
	@Override
	public String toString()
	{
		final String name=name();
		return name; 
	}
	
	/**
	 * Method to get GenderConstant object depending upon user input
	 * @param val
	 * @return GenderConstant object
	 */
	public static GenderConstant getName(int val)
	{
		GenderConstant gender=GenderConstant.OTHER;
		switch(val)
		{
		case 0:
			gender=GenderConstant.MALE;
			break;
		case 1:
			gender=GenderConstant.FEMALE;
			break;
			
		}
		return gender;
	}
	
	/**
	 * Method to convert String to GenderConstant object
	 * @param val
	 * @return GenderConstant object
	 */
	public static GenderConstant stringToGender(String val)
	{
		GenderConstant gender=GenderConstant.OTHER;
		if(val.equalsIgnoreCase("MALE"))
			gender=GenderConstant.MALE;
		else if(val.equalsIgnoreCase("FEMALE"))
			gender=GenderConstant.FEMALE;
		else if(val.equalsIgnoreCase("OTHER"))
			gender=GenderConstant.OTHER;
		
		return gender;
	}
	
}