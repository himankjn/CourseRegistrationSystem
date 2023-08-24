package com.wibmo.exception;

public class InvalidCourseAssignmentRequestException extends Exception{
	private String courseId;
	public InvalidCourseAssignmentRequestException( String courseId) {
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Course with courseId: "+ courseId+" not available. Please check the courseId again!";
	}	
}
