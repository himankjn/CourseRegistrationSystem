package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.GradeCard;

/**
 * 
 * @author Himank
 * Interface for Student course Registration Operations
 * 
 */
public interface RegistrationInterface {
	
		public boolean addCourse(String courseCode, String studentId);

	/**
	 * Method to set student registration status
	 * @param studentId
	 */
	void setRegistrationStatus(String studentId);

	/**
	 *  Method to check student registration status
	 * @param studentId
	 */
	boolean getRegistrationStatus(String studentId);
	
	/**
	 * Method to view the list of courses registered by the student
	 * @param studentId
	 */
	List<Course> viewRegisteredCourses(String studentId);

	/**
	 *  Method to view the list of available courses
	 * @param studentId
	 * @return List of courses
	 * @throws SQLException 
	 */
	List<Course> viewAvailableCourses(String studentId);
	/**
	 * Method to view grade card for students
	 * @param studentId
	 */
	GradeCard viewGradeCard(String studentId);

	/**
	 * Method to drop a course
	 * @param courseCode
	 * @param studentId
	 * @return
	 */
	boolean dropCourse(String courseCode, String studentId);

	/**
	 * Method to set payment status of student
	 * @param studentId
	 */
	public void setPaymentStatus(String studentId);
	
	/**
	 * Method to get payment status of student
	 * @param studentId
	 * @return
	 */
	boolean getPaymentStatus(String studentId);
}