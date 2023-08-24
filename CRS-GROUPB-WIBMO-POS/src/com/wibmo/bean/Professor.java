package com.wibmo.bean;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

public class Professor extends User{
	private String professorId;
	private String department;
	private String designation;
	
	public Professor(String userID) {
		super(userID);
	}
	public Professor(String userID, String name, GenderConstant gender, RoleConstant role, String password, String address) {
		super(userID, name, role, password, gender, address);
	}
	public Professor() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the professorId
	 */
	public String getProfessorId() {
		return professorId;
	}
	/**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	
	
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
}
