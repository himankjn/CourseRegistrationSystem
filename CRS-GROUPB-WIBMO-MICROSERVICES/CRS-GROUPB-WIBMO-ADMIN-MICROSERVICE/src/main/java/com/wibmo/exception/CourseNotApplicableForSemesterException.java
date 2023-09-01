/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class CourseNotApplicableForSemesterException extends Exception{
	private String courseId;
	private int semesterId;

	public CourseNotApplicableForSemesterException(String courseId, int semesterId) {
		super();
		this.courseId = courseId;
		this.semesterId = semesterId;
	}
	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the semesterId
	 */
	public int getSemesterId() {
		return semesterId;
	}
	/**
	 * @param semesterId the semesterId to set
	 */
	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Course with courseId: "+ courseId+" is not applicable for semester with semesterId :"+ semesterId;
	}
}
