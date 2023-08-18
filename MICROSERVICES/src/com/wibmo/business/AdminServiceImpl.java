/**
 * 
 */
package com.wibmo.business;

import com.wibmo.exception.*;
import com.wibmo.validator.AdminValidator;

import com.wibmo.bean.*;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.wibmo.dao.AdminDAOInterface;
import com.wibmo.dao.AdminDAOImpl;
/**
 * @author shanmukh
 *
 */

public class AdminServiceImpl implements AdminServiceInterface{
	private static final Logger logger = Logger.getLogger(AdminServiceImpl.class);
	private static volatile AdminServiceImpl instance = null;
	
	private AdminServiceImpl()
	{
		
	}
	
	/**
	 * Method to make AdminImpl Singleton
	 */
	
	public static AdminServiceImpl getInstance()
	{
		if(instance == null)
		{
			synchronized(AdminServiceImpl.class){
				instance = new AdminServiceImpl();
			}
		}
		return instance;
	}
	
	AdminDAOInterface adminDAOImpl =AdminDAOImpl.getInstance();
	
	
	public List<Course> viewCourses()
	{
		return adminDAOImpl.viewCourses();
	}
	public List<Professor> viewProfessors()
	{
		return adminDAOImpl.viewProfessors();
	}
	
	/**
	 * Method to view Students yet to be approved by Admin
	 * @return List of Students with pending admissions
	 */
	@Override
	public List<Student> viewPendingAdmissions() {
		return adminDAOImpl.viewPendingAdmissions();
	}
	
	/**
	 * Method to generate grade card of a Student 
	 * @param studentid 
	 */
	
	public List<RegisteredCourse> generateGradeCard(String Studentid)
	{
		return adminDAOImpl.generateGradeCard(Studentid);
	}
	
	/**
	 * Method to remove Course from Course Catalog
	 * @param courseCode
	 * @param courseList : Courses available in the catalog
	 * @throws CourseNotFoundException 
	 */
	@Override
	public void removeCourse(String dropCourseCode) throws CourseNotFoundException, CourseNotDeletedException {
		List<Course> courseList = viewCourses();
		if(!AdminValidator.isValidDropCourse(dropCourseCode, courseList)) {
			logger.info("courseCode: " + dropCourseCode + " not present in catalog!");
			throw new CourseNotFoundException(dropCourseCode);
		}
		
		adminDAOImpl.removeCourse(dropCourseCode);
	}

	/**
	 * Method to add Course to Course Catalog
	 * @param course : Course object storing details of a course
	 * @param courseList : Courses available in catalog
	 * @throws CourseFoundException
	 */
	@Override
	public void addCourse(Course newCourse) throws CourseExistsAlreadyException 
	{
		
		List<Course> courseList = viewCourses();
		try {
			if(!AdminValidator.isValidNewCourse(newCourse, courseList)) {
				logger.info("courseCode: " + newCourse.getCourseId() + " already present in catalog!");
				throw new CourseExistsAlreadyException(newCourse.getCourseId());
			}
			adminDAOImpl.addCourse(newCourse);
		}
		catch(CourseExistsAlreadyException e) {
			throw e;
		}
	}
	
	/**
	 * Method to approve a Student 
	 * @param studentId
	 * @param studentList 
	 * @throws StudentNotFoundException 
	 */
	@Override
	public void approveSingleStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException {
		
		
		try {
			
			if(AdminValidator.isValidUnapprovedStudent(studentId, studentList)) {
				
				throw new StudentNotFoundForApprovalException(studentId);
			}
			adminDAOImpl.approveSingleStudent(studentId);
		}
		catch(StudentNotFoundForApprovalException e) {
			
			throw e;
		}
	}
	
	@Override
	public void approveAllStudents(List<Student> studentList) {
		try {
			adminDAOImpl.approveAllStudents(studentList);
		}
		catch(Exception e) {
			logger.error("Could not approve students. Something went wrong!");
		}
	}

	/**
	 * @param professor : Professor Object storing details of a professor
	 * @throws ProfessorNotAddedException
	 */
	@Override
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyInUseException {
		adminDAOImpl.addProfessor(professor);
		
	}
	
	/**
	 * Method to assign Course to a Professor
	 * @param courseCode
	 * @param professorId
	 * @throws CourseNotFoundException 
	 * @throws UserNotFoundException 
	 */
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException
	{
		try {
		List<Professor> professors= viewProfessors();
		AdminValidator.verifyValidProfessor(professorId,professors);
		adminDAOImpl.assignCourse(courseCode, professorId);
		}
		catch(UserNotFoundException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void setGeneratedReportCardTrue(String Studentid) {
		adminDAOImpl.setGeneratedReportCardTrue(Studentid);
		
	}
	
	/**
	 * Method to view professors who requested for a particular course
	 * @param courseId
	 * @return
	 */
	public List<String> viewProfCourseRequests(String courseId){
		List<String> profIDs= adminDAOImpl.getProfCourseRequests(courseId);
		return profIDs;
	}

}
