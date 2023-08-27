package com.wibmo.entity;

import java.io.Serializable;

public class ProfessorCourseRequestId implements Serializable{
	private String courseId;
	private String userId;
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId=userId;
	}

}
