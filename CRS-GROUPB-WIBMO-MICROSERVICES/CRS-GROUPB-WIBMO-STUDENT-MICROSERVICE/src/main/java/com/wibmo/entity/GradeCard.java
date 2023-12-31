package com.wibmo.entity;

import java.io.Serializable;
import java.util.List;

public class GradeCard implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String studentId;
	double cgpa;
	int sem;
	List<RegisteredCourse> regList ;
	
	/**
	 * @return the stud
	 */
	public String getStudentId() {
		return studentId;
	}

	/**
	 * @param stud the stud to set
	 */
	public void setStudentId(String stud) {
		this.studentId = stud;
	}

	public int getSem() {
		return sem;
	}

	public void setSem(int sem) {
		this.sem = sem;
	}

	/**
	 * @return the cgpa
	 */
	public double getCgpa() {
		return cgpa;
	}

	/**
	 * @param d the cgpa to set
	 */
	public void setCgpa(double d) {
		this.cgpa = d;
	}

	/**
	 * @return the regList
	 */
	public List<RegisteredCourse> getRegList() {
		return regList;
	}

	/**
	 * @param regList the regList to set
	 */
	public void setRegList(List<RegisteredCourse> regList) {
		this.regList = regList;
	}
	
	
}
