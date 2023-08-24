package com.wibmo.bean;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

public abstract class User {
	private String userId;
	private String password;
	private GenderConstant gender;
	protected RoleConstant role;
	private String address;
	
	public GenderConstant getGender() {
		return gender;
	}
	public void setGender(GenderConstant gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
	
	public User(String userId) {
		this.userId = userId;
	}
	public User(String userId, String name,RoleConstant role,String password ,GenderConstant gender, String address) {
		this.userId = userId;
		this.name = name;
		this.gender = gender;
		this.role = role;
		this.password = password;
		this.address = address;
	}

	public User() {
		
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the role
	 */
	public RoleConstant getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(RoleConstant role) {
		this.role = role;
	}
}
