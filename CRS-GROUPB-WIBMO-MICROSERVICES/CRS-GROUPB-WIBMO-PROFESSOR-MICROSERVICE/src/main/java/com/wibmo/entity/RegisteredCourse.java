/**
 * 
 */
package com.wibmo.entity;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.wibmo.constants.GradeConstant;

import lombok.experimental.Delegate;

@Entity
@Table(name="registeredcourse")
public class RegisteredCourse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@Delegate
	private RegisteredCourseId registeredCourseId;
	

	@Enumerated(EnumType.STRING)
	@Column(name="grade")
	private GradeConstant grade;
	
	@Column(name="sem")
	private int sem;
	
	public int getSem() {
		return sem;
	}

	public void setSem(int sem) {
		this.sem = sem;
	}

	public RegisteredCourseId getRegisteredCourseId() {
		return registeredCourseId;
	}

	public void setRegisteredCourseId(RegisteredCourseId registeredCourseId) {
		this.registeredCourseId = registeredCourseId;
	}

	public GradeConstant getGrade() {
		return grade;
	}

	public void setGrade(GradeConstant grade) {
		this.grade = grade;
	}

	

}
