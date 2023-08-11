package com.wibmo.bean;

public class Professor extends User{
	private String professorId;
	private String department;
	private String position;
	
	public Professor(int userId,String name,String password,String address,String professorId,String department,String position)
	{
		super(userId,name,password,address);
		this.department = department;
		this.position = position;
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
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
}
