package com.wibmo.bean;

public class Student extends User{
	String studentId;
	String department;

	public Student(int userId,String name,String password,String address,String studentId,String department) {
		super(userId,name,password,address);
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

}
