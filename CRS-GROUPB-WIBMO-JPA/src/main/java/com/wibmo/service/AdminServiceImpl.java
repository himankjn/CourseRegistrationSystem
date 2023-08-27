/**
 * 
 */
package com.wibmo.service;

import com.wibmo.entity.*;
import com.wibmo.exception.*;
import com.wibmo.repository.AdminDAOInterface;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.ProfessorCourseRequestRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.validator.AdminValidator;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author shanmukh
 *
 */

@Service
public class AdminServiceImpl implements AdminServiceInterface{
	private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminDAOInterface adminDAOImpl;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Autowired
	private ProfessorCourseRequestRepository professorCourseRequestRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	
	public List<Course> viewCourses(){
		List<Course> courses = new ArrayList<Course>();  
		courseRepository.findAll().forEach(course -> courses.add(course));  
		return courses;  
	}
	
	public List<Professor> viewProfessors()
	{
		List<Professor> professors = new ArrayList<Professor>();  
		professorRepository.findAll().forEach(professor-> professors.add(professor));  
		return professors;
	}
	
	/**
	 * Method to view Students yet to be approved by Admin
	 * @return List of Students with pending admissions
	 */
	@Override
	public List<Student> viewPendingAdmissions() {
		//return adminDAOImpl.viewPendingAdmissions();
		List<Student> students = new ArrayList<Student>();  
		studentRepository.findAllByIsApproved(false).forEach(student-> students.add(student));  
		return students;
		
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
			courseRepository.save(newCourse);
		}
		catch(CourseExistsAlreadyException e) {
			throw e;
		}
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
		
		courseRepository.deleteById(dropCourseCode);
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
			studentRepository.setIsApprovedById(true, studentId);
		}
		catch(StudentNotFoundForApprovalException e) {
			
			throw e;
		}
	}
	
	/**
	 * Approve all pending students
	 */
	@Override
	public void approveAllStudents(List<Student> studentList) {
		try {
			studentRepository.setAllIsApproved(true);
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
		//adminDAOImpl.addProfessor(professor);
		professorRepository.save(professor);
		
	}
	
	/**
	 * Drop professor from db
	 * @param professorId: id of professor
	 * @throws ProfessorNotFoundException
	 */

	@Override
	public void dropProfessor(String professorId) throws ProfessorNotFoundException {
		List<Professor> professors = viewProfessors();
		if(!AdminValidator.isValidDropProfessor(professorId, professors)) {
			logger.info("professor: " + professorId + " not present in db!");
			throw new ProfessorNotFoundException(professorId);
		}
		professorRepository.deleteById(professorId);
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
		
		courseRepository.assignCourse(courseCode, professorId);
	}
	
	/**
	 * Method to generate grade card of a Student 
	 * @param studentid 
	 */
	
	public List<RegisteredCourse> generateGradeCard(String studentId)
	{
		List<RegisteredCourse> registeredCourses = new ArrayList<RegisteredCourse>();  
		registeredCourseRepository.findByStudentId(studentId).forEach(registeredcourse -> registeredCourses.add(registeredcourse)); 
		this.setGeneratedReportCardTrue(studentId);
		return registeredCourses;
		
	}

	@Override
	public void setGeneratedReportCardTrue(String studentId) {
		studentRepository.setGeneratedReportCardTrue(studentId);
		
	}
	
	/**
	 * Method to view professors who requested for a particular course
	 * @param courseId
	 * @return
	 */
	public List<String> viewProfCourseRequests(String courseId){
		List<String> profIds = new ArrayList<String>();  
		professorCourseRequestRepository.findByCourseId(courseId).forEach(courseRequest -> profIds.add(courseRequest.getuserId())); 
		return profIds;
		
	}

	

}
