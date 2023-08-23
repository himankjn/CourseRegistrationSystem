/**
 * 
 */
package com.wibmo.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.wibmo.bean.EnrolledStudent;
import java.util.ArrayList;

import com.wibmo.business.ProfessorServiceInterface;

import com.wibmo.bean.Course;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/professor")
public class ProfessorController {
	private static final Logger logger= LogManager.getLogger(ProfessorController.class);
	
	@Autowired
	private ProfessorServiceInterface professorService ;

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getCourses/{id}")
	public List<Course> getProfessorCourses(@PathVariable("id") String profID ) {
		logger.info("PROF ID: "+profID);
		List<Course> courseList = professorService.viewAssignedCourses(profID);
		return courseList;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/getEnrolledStudents/{id}")
	public List<EnrolledStudent> getEnrolledStudents(@PathVariable("id") String courseID) {
		List<EnrolledStudent> enrolledStudents = new ArrayList<EnrolledStudent>();
		enrolledStudents = professorService.viewEnrolledStudents(courseID);
		return enrolledStudents;	
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/addGrade/{sId}/{cId}/{grade}")
	public ResponseEntity addGradeOfStudent(@PathVariable("sId") String studentId,@PathVariable("cId") String courseId,@PathVariable("grade") String grade) {
		try {
			professorService.submitGrade(studentId, courseId, grade);
			return new ResponseEntity("Updated grade for student: "+studentId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("GradeConstant cannot be added for "+ studentId, HttpStatus.NOT_ACCEPTABLE);
		}	
	}
				
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT, value = "/requestCourse/{pId}/{cId}")
	public ResponseEntity requestCourseAssignment(@PathVariable("pId") String profID,@PathVariable("cId") String courseId) {
		try {
			List<Course> unassignedCourses= professorService.getUnassignedCourses();
			logger.info("Enter the courseID you want to request:");
			professorService.requestCourseAssignment(profID, courseId);
			return new ResponseEntity(courseId, HttpStatus.OK);

	} catch (Exception e) {
		return new ResponseEntity("Request unsuccessful! Something went wrong. Please contact admin.", HttpStatus.NOT_ACCEPTABLE);
	}
	}
	
		
	
}
	