package com.wibmo.bean;

import org.springframework.stereotype.Component;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

@Component
public class Admin extends User{
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
