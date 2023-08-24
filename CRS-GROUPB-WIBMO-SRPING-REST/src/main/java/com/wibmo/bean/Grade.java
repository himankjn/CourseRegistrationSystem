/**
 * 
 */
package com.wibmo.bean;

import java.io.Serializable;

/**
 * @author himank
 *
 */

public class Grade implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseCode;
	private String courseName;
	private String grade;
	
	public Grade() {
		
	}
	
	public Grade(String courseCode, String courseName, String grade) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.grade = grade;
	}


	/**
	 * @return the crsCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	
	/**
	 * @param crsCode the crsCode to set
	 */
	public void setCourseCode(String crsCode) {
		this.courseCode = crsCode;
	}
	
	
	/**
	 * @return the crsName
	 */
	public String getCourseName() {
		return courseName;
	}
	
	
	/**
	 * @param crsName the crsName to set
	 */
	public void setCourseName(String crsName) {
		this.courseName = crsName;
	}
	
	
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	
	
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
	
}
