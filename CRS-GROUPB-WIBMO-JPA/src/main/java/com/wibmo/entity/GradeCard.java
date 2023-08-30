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
	List<RegisteredCourse> reg_list ;
	
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
	 * @return the reg_list
	 */
	public List<RegisteredCourse> getReg_list() {
		return reg_list;
	}

	/**
	 * @param reg_list the reg_list to set
	 */
	public void setReg_list(List<RegisteredCourse> reg_list) {
		this.reg_list = reg_list;
	}
	
	
}
