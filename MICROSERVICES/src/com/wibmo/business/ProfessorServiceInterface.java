package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.bean.Student;

/**
 * @author Himank
 *
 */
public interface ProfessorServiceInterface {
	
	/**
	 * Method to submit grade for a student for particular course
	 * @param studentID
	 * @param courseID
	 * @param grade
	 * @return
	 */
	
	public boolean submitGrade(String studentID, String courseID, String grade);
	
	/**
	 * Method to view all courses taught by a professor
	 * @param profID
	 * @return
	 */
	
	public List<Course> viewCourses(String profID);

	/**
	 * Method to view all the enrolled students for a course
	 * @param profId: professor id 
	 * @return List of enrolled students
	 */
	public List<EnrolledStudent> viewEnrolledStudents(String courseId);

	
}