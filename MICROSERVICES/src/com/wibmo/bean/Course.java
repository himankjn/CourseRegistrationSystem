package com.wibmo.bean;

public class Course {
	private String courseId;
	private int credits;
	private String courseName;
	private String professorId;
	public Course(String courseId, int credits, String courseName, String professorId) {
		this.courseId=courseId;
		this.credits=credits;
		this.courseName=courseName;
		this.professorId=professorId;
	}
	/**
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	/**
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}
	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the name
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.courseName = name;
	}
	/**
	 * @return the instructor
	 */
	public String getProfessorId() {
		return professorId;
	}
	/**
	 * @param instrutctor the instructor to set
	 */
	public void setInstrutctor(String professorId) {
		this.professorId = professorId;
	}
}
