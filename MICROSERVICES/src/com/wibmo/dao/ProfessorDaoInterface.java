/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;

/**
 * @author Shanmukh
 *
 */
public interface ProfessorDaoInterface {
	
	/**
	 * Method to get Courses by Professor Id using SQL Commands
	 * @param userId, prof id of the professor
	 */
	public List<Course> getCoursesByProfessor(String profId);
	
	
	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code of the professor
	 */
	public List<Student> getEnrolledStudents(String courseId);
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code for the corresponding 
	 */
	public Boolean addGrade(String studentId,String courseCode,String grade);

}
