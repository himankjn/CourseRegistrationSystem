/**
 * 
 */
package com.wibmo.bean;


/*
 * @author bhuvan
 *
 */

import com.wibmo.constants.GradeConstant;

public class RegisteredCourse 
{
	Course course;
	String studentId;
	GradeConstant grade;
	
	
	
	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}
	
	
	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = new Course(course.getCourseId(), course.getCourseName(), course.getInstructorId() ,course.getSeats());
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
