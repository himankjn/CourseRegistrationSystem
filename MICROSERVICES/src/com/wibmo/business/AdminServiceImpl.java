/**
 * 
 */
package com.wibmo.business;

import com.wibmo.exception.*;
import com.wibmo.validator.AdminValidator;

import java.util.List;

import com.wibmo.bean.*;
import com.wibmo.dao.AdminDAOInterface;
import com.wibmo.dao.AdminDAOImpl;
/**
 * @author shanmukh
 *
 */

public class AdminServiceImpl implements AdminServiceInterface{
	
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
	 * studentid 
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
	public void removeCourse(String dropCourseCode, List<Course> courseList) throws CourseNotFoundException, CourseNotDeletedException {
		if(!AdminValidator.isValidDropCourse(dropCourseCode, courseList)) {
			System.out.println("courseCode: " + dropCourseCode + " not present in catalog!");
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
	public void addCourse(Course newCourse, List<Course> courseList) throws CourseExistsAlreadyException 
	{
		
		
		try {
			if(!AdminValidator.isValidNewCourse(newCourse, courseList)) {
				System.out.println("courseCode: " + newCourse.getCourseId() + " already present in catalog!");
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
	public void approveStudent(String studentId, List<Student> studentList) throws StudentNotFoundForApprovalException {
		
		
		try {
			
			if(AdminValidator.isValidUnapprovedStudent(studentId, studentList)) {
				
				throw new StudentNotFoundForApprovalException(studentId);
			}
			adminDAOImpl.approveStudent(studentId);
		}
		catch(StudentNotFoundForApprovalException e) {
			
			throw e;
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
		adminDAOImpl.assignCourse(courseCode, professorId);
	}

	@Override
	public void setGeneratedReportCardTrue(String Studentid) {
		adminDAOImpl.setGeneratedReportCardTrue(Studentid);
		
	}

}
