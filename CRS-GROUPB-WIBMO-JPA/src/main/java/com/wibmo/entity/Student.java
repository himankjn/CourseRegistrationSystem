package com.wibmo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

@Entity
@Table(name="student")
public class Student extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Column(name="department")
	private String department;
	@Column(name="studentId")
	private String studentId;
	@Column(name="sem")
	private int sem;
	@Column(name="gradYear")
	private int gradYear;
	@Column(name="isApproved")
	boolean isApproved;
	@Column(name="isRegistered")
	boolean isRegistered;
	@Column(name="isPaid")
	boolean isPaid;
	@Column(name="isReportGenerated")
	boolean isReportGenerated;
	
	
	
	
	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public int getSem() {
		return sem;
	}

	public void setSem(int sem) {
		this.sem = sem;
	}


	public boolean isReportGenerated() {
		return isReportGenerated;
	}

	public void setReportGenerated(boolean isReportGenerated) {
		this.isReportGenerated = isReportGenerated;
	}

	
	public int getGradYear() {
		return gradYear;
	}

	public void setGradYear(int gradYear) {
		this.gradYear = gradYear;
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
