package com.wibmo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="admin")
public class Admin extends User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="adminId")
	private String adminId;
	


	public String getAdminId() {
		return adminId;
	}

	public void setAdminID(String adminId) {
		this.adminId = adminId;
	}	

}
