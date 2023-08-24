/**
 * 
 */
package com.wibmo.business;

import com.wibmo.exception.*;
import com.wibmo.validator.AdminValidator;


import com.wibmo.bean.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.AdminDAOInterface;
import com.wibmo.dao.AdminDAOImpl;
/**
 * @author shanmukh
 *
 */

@Service
public class AdminServiceImpl implements AdminServiceInterface{
	private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminDAOInterface adminDAOImpl;
	
	
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
	public void approveSingleStudent(String studentId) throws StudentNotFoundForApprovalException {
		
	List<Student> studentList=viewPendingAdmissions();
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
		List<Professor> professors= viewProfessors();
		List<Course> courses= viewCourses();
		AdminValidator.verifyValidProfessor(professorId,professors);
		AdminValidator.verifyValidCourse(courseCode,courses);
		adminDAOImpl.assignCourse(courseCode, professorId);
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

	@Override
	public void dropProfessor(String professorId) throws ProfessorNotFoundException {
		List<Professor> professors = viewProfessors();
		if(!AdminValidator.isValidDropProfessor(professorId, professors)) {
			logger.info("professor: " + professorId + " not present in db!");
			throw new ProfessorNotFoundException(professorId);
		}
		
		adminDAOImpl.dropProfessor(professorId);
	}

}
