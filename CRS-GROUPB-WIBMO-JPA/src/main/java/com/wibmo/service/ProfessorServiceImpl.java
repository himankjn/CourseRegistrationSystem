package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.entity.Course;
import com.wibmo.entity.EnrolledStudent;
import com.wibmo.exception.CourseAlreadyAssignedException;
import com.wibmo.exception.InvalidCourseAssignmentRequestException;
import com.wibmo.repository.ProfessorDAOImpl;
import com.wibmo.repository.ProfessorDAOInterface;
import com.wibmo.validator.ProfessorValidator;


/**
 * @author Himank
 */

@Service
public class ProfessorServiceImpl implements ProfessorServiceInterface {
	private static final Logger logger = LogManager.getLogger(ProfessorServiceImpl.class);
	
	@Autowired
	private ProfessorDAOInterface professorDAOInterface;
	/**
	 * Method to submit student grade for a course;
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 */
	public boolean submitGrade(String studentId,String courseCode,String grade){
		professorDAOInterface.addGrade(studentId, courseCode, grade);
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
			enrolledStudents=professorDAOInterface.getEnrolledStudents(courseId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getStackTrace());
		}
		return enrolledStudents;
	}

	
	/**
	 * Method to get list of all course for a professor
	 * @param profId: professor id
	 */
	
	public List<Course> viewAssignedCourses(String profId) {
		List<Course> coursesOffered=new ArrayList<Course>();
		try
		{
			coursesOffered=professorDAOInterface.getCoursesByProfessor(profId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getStackTrace());
		}
		return coursesOffered;
	}

	@Override

    public List<Course> getUnassignedCourses() {
        List<Course> coursesUnAssigned=new ArrayList<Course>();
        try

        {
            coursesUnAssigned = professorDAOInterface.getUnassignedCourses();
        }
        catch(Exception ex)
        {
            logger.error(ex.getStackTrace());
        }
        return coursesUnAssigned;
    }
	
	@Override
	public boolean requestCourseAssignment(String userId, String courseId) {
		
		try {
			List<Course> unassignedCourses=getUnassignedCourses();
			ProfessorValidator.verifyValidCourseRequest(courseId, unassignedCourses);
			boolean res=professorDAOInterface.sendCourseAssignmentRequest(userId,courseId);
			return res;
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