package com.wibmo.entity;

import java.io.Serializable;

public class RegisteredCourseId implements Serializable{
	private String courseId;
	private String studentId;
	
	public RegisteredCourseId(){
		
	}
	private RegisteredCourseId(String courseId, String studentId){
		this.setCourseId(courseId);
		this.setStudentId(studentId);
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
}
