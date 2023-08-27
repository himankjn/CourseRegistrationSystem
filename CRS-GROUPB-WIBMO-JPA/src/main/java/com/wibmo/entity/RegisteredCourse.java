/**
 * 
 */
package com.wibmo.entity;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.wibmo.constants.GradeConstant;

@Entity
@Table(name="registeredcourse")
@IdClass(RegisteredCourseId.class)
public class RegisteredCourse implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@Id
	String courseId;
	@Column
	@Id
	String studentId;
	@Column
	GradeConstant grade;
	
	
	
	/**
	 * @return the course
	 */
	public String getCourseId() {
		return courseId;
	}
	
	
	/**
	 * @param course the course to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	
	/*
	 * @return the studentId
	 */
	public String getstudentId() {
		return studentId;
	}
	
	
	/**
	 * @param studentId the studentId to set
	 */
	public void setstudentId(String studentId) {
		this.studentId = studentId;
	}
	
	
	/**
	 * @return the grade
	 */
	public GradeConstant getGrade() {
		return grade;
	}
	
	
	
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(GradeConstant grade) {
		this.grade = grade;
	}
	
	
	
	

}
