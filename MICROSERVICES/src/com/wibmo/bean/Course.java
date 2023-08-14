package com.wibmo.bean;

public class Course {
	private int credits;
	private String courseId;
	private String courseName;
	private String instructorId;
	private int studentsEnrolled;
	
	public Course(String courseId, int credits, String courseName, String instructorId) {
		this.courseId=courseId;
		this.credits=credits;
		this.courseName=courseName;
		this.instructorId=instructorId;
		this.studentsEnrolled = 0;
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
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

	/**
	 * @return the studentsEnrolled
	 */
	public int getStudentsEnrolled() {
		return studentsEnrolled;
	}

	/**
	 * @param studentsEnrolled the studentsEnrolled to set
	 */
	public void setStudentsEnrolled(int studentsEnrolled) {
		this.studentsEnrolled = studentsEnrolled;
	}

}
