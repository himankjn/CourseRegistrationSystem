package com.wibmo.bean;

import java.io.Serializable;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

public class Student extends User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String department;
	private String studentId;
	private int gradYear;
	boolean isApproved;
	
	public int getGradYear() {
		return gradYear;
	}

	public void setGradYear(int gradYear) {
		this.gradYear = gradYear;
	}

	
	
	public Student(String userId, String name, RoleConstant role, String password, GenderConstant gender, String address,String department,String studentId,int gradYear,boolean isApproved) {
		super(userId, name, role, password,gender,address);
		this.department = department;
		this.studentId = studentId;
		this.gradYear = gradYear;
		this.isApproved = isApproved;
	}

	public Student() {
		
	}
	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
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
	

	/**
	 * @return the isApproved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * @param isApproved the isApproved to set
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

}
