package com.wibmo.bean;

public class Admin extends User{
	private String adminId;
	public Admin(int userID, String role, String password, String address,String adminId) 
	{
		super(userID,password,role);
		this.adminId = adminId;
	}
	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}
	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

}
