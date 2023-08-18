/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.exception.CourseExistsAlreadyException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyInUseException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;

/**
 * @author shanmukh
 *
 */
public interface AdminDAOInterface {
	
	public List<Course> viewCourses();
	public List<Professor> viewProfessors();
	
	
	/**
	 * Method to generate grade card of a Student 
	 * studentid 
	 * @return 
	 * 
	 * 
	 */
	
	public void setGeneratedReportCardTrue(String Studentid);
	
	public List<RegisteredCourse> generateGradeCard(String Studentid);
	
	/**
	 * Fetch Students yet to approved using SQL commands
	 * @return List of Students yet to approved
	 */
	public List<Student> viewPendingAdmissions();
	
	/**
	 * Method to approve a Student 
	 * studentid
	 * studentlist
	 */
	
	
	public void approveSingleStudent(String studentid) throws StudentNotFoundForApprovalException;
	
	public void approveAllStudents(List<Student> studentList);
	
	/**
	 * Method to add Professor to DB
	 * professor : Professor Object storing details of a professor 
	 */
	
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyInUseException;
	
	/**
	 * Method to Delete Course from Course Catalog
	 * @param courseCode
	 * @param courseList : Courses available in the catalog
	 * @throws CourseNotFoundException 
	 * @throws CourseNotDeletedException 
	 */

	public void removeCourse(String coursecode) throws CourseNotFoundException, CourseNotDeletedException;
	
	/**
	 * Method to add Course to Course Catalog
	 * @param course : Course object storing details of a course
	 * @param courseList : Courses available in the catalog
	 * @throws CourseExistsAlreadyException;
	 */
	
	public void addCourse(Course course) throws CourseExistsAlreadyException;
	
	/**
	 * Method to assign Course to a Professor
	 * @param courseCode
	 * @param professorId
	 * @throws CourseNotFoundException 
	 * @throws UserNotFoundException 
	 */
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException;
	
	public void addUser(User user) throws UserNotAddedException, UserIdAlreadyInUseException;

	public List<String> getProfCourseRequests(String courseId);
}
