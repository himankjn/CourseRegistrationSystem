package com.wibmo.bean;

import java.io.Serializable;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

public class Admin extends User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adminID;
	
	public Admin() {
		
	}
	
	public Admin(String userID, String name, GenderConstant gender, RoleConstant role, String password, String address) 
	{
		super(userID, name, role, password, gender, address);
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}	

}
