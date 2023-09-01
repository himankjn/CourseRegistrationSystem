/**
 * 
 */
package com.wibmo.exception;

/**
 * Exception class to handle Course already assigned to a professor exception
 */
public class CourseAlreadyAssignedException extends Exception{
	
	private String userId;
	private String courseId;
	public CourseAlreadyAssignedException(String userId, String courseId) {
		this.userId = userId;
		this.courseId = courseId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "course with courseId: "+ courseId+" has already been assigned to prof :"+ userId;
	}	
}
