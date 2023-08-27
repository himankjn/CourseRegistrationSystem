package com.wibmo.entity;

import java.io.Serializable;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

public class Admin extends User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adminID;
	


	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}	

}
