package com.wibmo.constants;

/**
 * 
 * @author himank
 * Enumeration class for RoleConstant Types
 *
 */
public enum RoleConstant {
	ADMIN(0),PROFESSOR(1),STUDENT(2);
	
	private final int role;
	
	private RoleConstant(int role)
	{
		this.role=role;
	}
	@Override
	public String toString()
	{
		final String name=name();
		return name; 
	}
	
	/**
	 * Method to get RoleConstant object from String
	 * @param role
	 * @return RoleConstant object
	 */
	public static RoleConstant stringToName(String role)
	{
		RoleConstant userRole=null;

		if(role.equalsIgnoreCase("ADMIN"))
			userRole=RoleConstant.ADMIN;
		else if(role.equalsIgnoreCase("PROFESSOR"))
			userRole=RoleConstant.PROFESSOR;
		else if(role.equalsIgnoreCase("STUDENT"))
			userRole=RoleConstant.STUDENT;
		return userRole;
	}
}