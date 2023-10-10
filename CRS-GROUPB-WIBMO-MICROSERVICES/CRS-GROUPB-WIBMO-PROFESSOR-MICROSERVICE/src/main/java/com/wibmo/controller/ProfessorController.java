/**
 * 
 */
package com.wibmo.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import com.wibmo.entity.Course;
import com.wibmo.entity.EnrolledStudent;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.ProfessorServiceInterface;
import com.wibmo.service.UserServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
@PreAuthorize("hasAuthority('PROFESSOR')")
@RequestMapping(value = "/professor")
@CrossOrigin(origins = "*")
public class ProfessorController {
	private static final Logger logger= LogManager.getLogger(ProfessorController.class);
	
	@Autowired
	private ProfessorServiceInterface professorService ;
	
	@Autowired
	private UserServiceInterface userService;
	
	/**
	 * update password of User
	 */
	
	@RequestMapping(value="updatePassword/{id}/{pass}",method=RequestMethod.PUT)
	public ResponseEntity updatePassword(@PathVariable("id") String userId,@PathVariable("pass") String password) {
			boolean isUpdated;
			try {
				isUpdated = userService.updatePassword(userId, password);
			} catch (UserNotFoundException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(isUpdated) {
				return new ResponseEntity("Password Updated!",HttpStatus.OK);
			}
			else {
				return new ResponseEntity("Unable to update password",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
	
	/**
	 * Get assigned courses for a professor
	 * @param profID
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getCourses/{id}")
	public ResponseEntity getProfessorCourses(@PathVariable("id") String profID ) {
		logger.info("PROF ID: "+profID);
		List<Course> courseList = professorService.viewAssignedCourses(profID);
		return new ResponseEntity(courseList,HttpStatus.OK);
	}
	
	/**
	 * Method to get list of all course that can be requested and are unassigned
	 * @param profId: professor id
	 */
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getUnassignedCourses")
	public ResponseEntity getUnassignedCourses( ) {
		List<Course> courseList = professorService.getUnassignedCourses();
		return new ResponseEntity(courseList,HttpStatus.OK);
	}
	
	/**
	 * Get enrolled students for a course
	 * @param courseID
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getEnrolledStudents/{id}")
	public ResponseEntity getEnrolledStudents(@PathVariable("id") String courseID) {
		List<EnrolledStudent> enrolledStudents = new ArrayList<EnrolledStudent>();
		enrolledStudents = professorService.viewEnrolledStudents(courseID);
		return new ResponseEntity(enrolledStudents,HttpStatus.OK);
	}
	
	/**
	 * Submit grade for a student for a course
	 * @param studentId
	 * @param courseId
	 * @param grade
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/addGrade/{sId}/{cId}/{grade}")
	public ResponseEntity addGradeOfStudent(@PathVariable("sId") String studentId,@PathVariable("cId") String courseId,@PathVariable("grade") String grade) {
		try {
			professorService.submitGrade(studentId, courseId, grade);
			return new ResponseEntity("Updated grade for student: "+studentId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("GradeConstant cannot be added for "+ studentId, HttpStatus.NOT_ACCEPTABLE);
		}	
	}
				
	/**
	 * Provides list of all professor requests for a course
	 * @param profID
	 * @param courseId
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/requestCourse/{pId}/{cId}")
	public ResponseEntity requestCourseAssignment(@PathVariable("pId") String profID,@PathVariable("cId") String courseId) {
		try {
			professorService.requestCourseAssignment(profID, courseId);
			return new ResponseEntity(courseId, HttpStatus.OK);
		}
	 catch (Exception e) {
		return new ResponseEntity("Request unsuccessful! Something went wrong. Please contact admin.", HttpStatus.NOT_ACCEPTABLE);
	}
	}
	
		
	
}
	