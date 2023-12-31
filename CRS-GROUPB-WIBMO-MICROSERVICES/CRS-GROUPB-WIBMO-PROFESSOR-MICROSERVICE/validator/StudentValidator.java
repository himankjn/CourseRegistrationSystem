package com.wibmo.validator;

import java.util.List;

import com.wibmo.entity.Course;
import com.wibmo.exception.CourseNotApplicableForSemesterException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.PasswordMismatchException;

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


	public static void verifySemesterMatch(String course,int sem1,int sem2) throws CourseNotApplicableForSemesterException {
		if(sem1!=sem2)
			throw new CourseNotApplicableForSemesterException(course,sem1);
	}
	

}