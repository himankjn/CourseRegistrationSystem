package com.wibmo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="profCourseRequest")
@IdClass(ProfessorCourseRequestId.class)
public class ProfessorCourseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@Id
	String courseId;
	@Column
	@Id
	String userId;
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getuserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
	}
}
