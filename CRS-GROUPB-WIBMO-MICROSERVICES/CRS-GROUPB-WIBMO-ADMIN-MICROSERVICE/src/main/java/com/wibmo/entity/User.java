package com.wibmo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

@Entity
@Table(name="user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="userId")
	@Id
	private String userId;
	@Column(name="password")
	private String password;

	@Enumerated
	@Column(name="gender")
	private GenderConstant gender;
	
	@Column(name="role")
	@Enumerated
	protected RoleConstant role;
	@Column(name="address")
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
