package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.GradeConstant;
import com.wibmo.entity.Course;
import com.wibmo.entity.EnrolledStudent;
import com.wibmo.entity.ProfessorCourseRequest;
import com.wibmo.exception.CourseAlreadyAssignedException;
import com.wibmo.exception.InvalidCourseAssignmentRequestException;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.ProfessorCourseRequestRepository;
import com.wibmo.repository.ProfessorDAOImpl;
import com.wibmo.repository.ProfessorDAOInterface;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.validator.ProfessorValidator;


/**
 * @author Himank
 */

@Service
public class ProfessorServiceImpl implements ProfessorServiceInterface {
	private static final Logger logger = LogManager.getLogger(ProfessorServiceImpl.class);
	
	
	@Autowired
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Autowired 
	private CourseRepository courseRepository;
	
	@Autowired
	private ProfessorCourseRequestRepository professorCourseRequestRepository;
	
	
	/**
	 * Method to get list of all course for a professor
	 * @param profId: professor id
	 */
	
	public List<Course> viewAssignedCourses(String profId) {
		List<Course> coursesOffered=new ArrayList<Course>();
		courseRepository.findByInstructorId(profId).forEach(course -> coursesOffered.add(course));
		return coursesOffered;
	}

	/**
	 * Method to get list of all course that can be requested and are unassigned
	 * @param profId: professor id
	 */
	@Override
	public List<Course> getUnassignedCourses() {
        List<Course> coursesUnAssigned=new ArrayList<Course>();
        courseRepository.findByInstructorId("NOT_ASSIGNED").forEach(course -> coursesUnAssigned.add(course));
        return coursesUnAssigned;
    }
	
	/**
	 * Method to submit student grade for a course;
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 */
	public boolean submitGrade(String studentId,String courseId,String grade){
		//registeredCourseRepository.addGrade(studentId, courseId, grade);
		try {
			registeredCourseRepository.addGrade(studentId,courseId,GradeConstant.valueOf(grade));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * Method to view all the enrolled students
	 * @param coursxeId: Course id 
	 */
	
	public List<EnrolledStudent> viewEnrolledStudents(String courseId) {
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try
		{
			//enrolledStudents=professorDAOInterface.getEnrolledStudents(courseId);
			//"select course.courseId,course.courseName,registeredcourse.studentId from course 
			//inner join registeredcourse on course.courseId = registeredcourse.courseId 
			//where course.courseId=?";
			registeredCourseRepository.getEnrolledStudents(courseId).forEach(student-> enrolledStudents.add(student));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return enrolledStudents;
	}

	
	
	
	
	@Override
	public boolean requestCourseAssignment(String userId, String courseId) {
		
		try {
			List<Course> unassignedCourses=getUnassignedCourses();
			ProfessorValidator.verifyValidCourseRequest(courseId, unassignedCourses);
			ProfessorCourseRequest professorCourseRequest =new ProfessorCourseRequest();
			professorCourseRequest.setCourseId(courseId);
			professorCourseRequest.setuserId(userId);
			ProfessorCourseRequest res=	professorCourseRequestRepository.save(professorCourseRequest);
			return res!=null;
		}
		catch(InvalidCourseAssignmentRequestException e){
			logger.error(e.getMessage());
		}
		catch(Exception e) {
			logger.error("Something went wrong!");
		}
		return false;
	}
	
}