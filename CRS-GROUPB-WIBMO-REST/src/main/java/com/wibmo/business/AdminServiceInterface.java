
/**
 * 
 */
package com.wibmo.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wibmo.exception.*;

import com.wibmo.bean.*;

/**
 * @author shanmukh
 */

@Service
public interface AdminServiceInterface 
{
	
	public void setGeneratedReportCardTrue(String Studentid);
	
	/**
	 * Method to get list of courses in catalog
	 * @param catalogId
	 * @return List of courses in catalog
	 */
	public List<Course> viewCourses();
	
	/**
	 * View professor in the institute
	 * @return List of the professors in the institute  
	 */
	public List<Professor> viewProfessors();
	
	/**
	 * Method to view Students yet to be approved by Admin
	 * @return List of Students with pending admissions
	 */
	public List<Student> viewPendingAdmissions();
	
	/**
	 * Method to generate grade card of a Student 
	 * studentid 
	 * @return 
	 */
	public List<RegisteredCourse> generateGradeCard(String Studentid);
	
	/**
	 * Method to approve a Student 
	 * studentid
	 * studentlist
	 */
	
	public void approveSingleStudent(String studentid, List<Student> studentlist) throws StudentNotFoundForApprovalException;
	
	
	public void approveAllStudents(List<Student> studentList);
	
	/**
	 * Method to add Professor to DB
	 * professor : Professor Object storing details of a professor 
	 */
	
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyInUseException;
	
	/**
	 * Method to drop professor
	 * @param professorId
	 */
	public void dropProfessor(String professorId);
	
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
	
	/**
	 * Method to view professors who requested for a particular course
	 * @param courseId
	 * @return
	 */
	public List<String> viewProfCourseRequests(String courseId);
}
