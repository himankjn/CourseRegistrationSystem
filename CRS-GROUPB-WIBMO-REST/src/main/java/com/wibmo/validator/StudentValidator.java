package com.wibmo.validator;

import java.util.List;

import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.PasswordMismatchException;
import com.wibmo.exception.SeatNotAvailableException;

import com.wibmo.bean.Course;

/**
 * 
 * @author bhuvan
 * Class for Student Validator 
 * 
 */
public class StudentValidator {

	/**
	 * Method to validate if student is already registered for this particular course (courseCode) or not 
	 * @param courseCode
	 * @param studentId
	 * @param registeredCourseList  
	 * @return Student Registration Status
	 * @throws CourseNotFoundException
	 */
	public static boolean isRegistered(String courseCode,String studentId,List<Course>registeredCourseList) throws CourseNotFoundException
	{
		for(Course course : registeredCourseList)
		{
			if(courseCode.equalsIgnoreCase(course.getCourseId())) 
			{
				return true; 
			}
		}
		
		return false;
	}
	
	
	/**
	 * Method to validate if couseCode is valid or not
	 * @param courseCode
	 * @param availableCourseList
	 * @return couseCode is valid or not
	 */
	public static boolean isValidCourseCode(String courseCode,List<Course>availableCourseList) 
	{
		for(Course course : availableCourseList)
		{
			if(courseCode.equalsIgnoreCase(course.getCourseId())) 
			{
				return true; 
			}
		}
		
		return false;
	
	}
	
	public static void verifySamePassword(String password, String confirmPassword) throws PasswordMismatchException
	{
		if(!password.equals(confirmPassword)) {
			throw new PasswordMismatchException(password,confirmPassword);
		}
	}
	

}