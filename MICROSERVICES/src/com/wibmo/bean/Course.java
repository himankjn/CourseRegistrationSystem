package com.wibmo.bean;

public class Course {
	private int courseId;
	private int credits;
	private String name;
	private String instrutctor;
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
	public int getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the instructor
	 */
	public String getInstrutctor() {
		return instrutctor;
	}
	/**
	 * @param instrutctor the instructor to set
	 */
	public void setInstrutctor(String instrutctor) {
		this.instrutctor = instrutctor;
	}
}
