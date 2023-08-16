package com.wibmo.bean;

public class Student extends User{
	String studentId;
	String name;
	String department;
	boolean feePaid;
	boolean isApproved;
	
	public Student(int userId,String password,String role,String name,String studentId,String department) {
		super(userId,password,role);
		this.studentId = studentId;
		this.department = department;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the feePaid
	 */
	public boolean isFeePaid() {
		return feePaid;
	}

	/**
	 * @param feePaid the feePaid to set
	 */
	public void setFeePaid(boolean feePaid) {
		this.feePaid = feePaid;
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
