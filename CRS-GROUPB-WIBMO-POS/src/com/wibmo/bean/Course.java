package com.wibmo.bean;

public class Course {
	private String courseId;
	private String courseName;
	private String instructorId;
	private int seats = 10;
	
	public Course()
	{
		
	}
	
	public Course(String courseId,String courseName,String professorId,int seats) {
		this.courseId=courseId;
		this.courseName=courseName;
		this.instructorId=professorId;
		this.setSeats(seats);
	}

	public String getCourseId() {
		return courseId;
	}
	/**
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
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

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	

}
