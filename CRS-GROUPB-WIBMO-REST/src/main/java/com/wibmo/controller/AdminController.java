package com.wibmo.controller;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Course;
import com.wibmo.bean.GradeCard;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.business.AdminServiceInterface;
import com.wibmo.business.NotificationServiceInterface;
import com.wibmo.business.RegistrationServiceInterface;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.exception.CourseExistsAlreadyException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyInUseException;
import com.wibmo.exception.UserNotFoundException;

@RestController
@RequestMapping(value="/admin")
public class AdminController {
	private static final Logger logger= LogManager.getLogger(AdminController.class);
	
	
	@Autowired
	private AdminServiceInterface adminService;
	
	@Autowired
	private NotificationServiceInterface notificationService;
	
	@Autowired
	private RegistrationServiceInterface registrationService;

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/hello-world")
	public String helloWorld() {	
		return "Hello World";
	}
	
	@GetMapping("/viewCourseCatalog")
	public List<Course> viewCoursesInCatalog() {
		logger.info("Retrieving courses from catalog.");
		List<Course> courseList = adminService.viewCourses();
		return courseList;
	}
	
	
	/*
	 {
	    "courseId": "CPP",
	    "courseName": "CPlusCplus",
	    "instructorId": "bob123@gmail.com",
	    "seats": 10
	 }
	 */
	@PostMapping(consumes=MediaType.APPLICATION_JSON ,value= "/addCourse")
	public ResponseEntity addCourseToCatalog(@RequestBody Course course) {
		try {
			adminService.addCourse(course);
			return new ResponseEntity(course, HttpStatus.OK);
		} catch (CourseExistsAlreadyException e) {
			return new ResponseEntity("Course with id: "+ course.getCourseId()+" already exists!", HttpStatus.NOT_ACCEPTABLE);
		}	
	}
	
	
	//localhost:8080/removeCourseFromCatalog/SB
	
	@DeleteMapping(value="/removeCourse/{id}")
	public ResponseEntity removeCourse(@PathVariable("id") String courseId) {
		try {
			adminService.removeCourse(courseId);
			return new ResponseEntity("Course Deleted : " + courseId, HttpStatus.OK);
		} catch (CourseNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		catch (CourseNotDeletedException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_MODIFIED);
		}
	}	
	
	
	@PutMapping(value="/approveStudent/{id}")
	public ResponseEntity approveSingleStudent(@PathVariable("id") String studentId) {
		try {
				adminService.approveSingleStudent(studentId);
				//send notification from system
				notificationService.sendNotification(NotificationTypeConstant.APPROVED, studentId, null,0);
				return new ResponseEntity("Student Id : " +studentId+ " has been approved.", HttpStatus.OK);
		
			} 
		catch (StudentNotFoundForApprovalException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
			}
		
	}
	
	@PutMapping(value="/approveStudent")
	public ResponseEntity approveAllStudents() {
		List<Student> studentList= adminService.viewPendingAdmissions();
		try {
			adminService.approveAllStudents(studentList);
			//notify
			for(Student student: studentList){
				notificationService.sendNotification(NotificationTypeConstant.APPROVED, student.getUserId(), null,0);
			}
			return new ResponseEntity("All students approved!",HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@GetMapping(value="/pendingAdmissions")
	public ResponseEntity viewPendingAdmissions() {
		List<Student> pendingStudentsList= adminService.viewPendingAdmissions();
		if(pendingStudentsList.size() == 0) {
			return new ResponseEntity("No students pending approvals",HttpStatus.OK);
		}
		else
			return new ResponseEntity(pendingStudentsList,HttpStatus.OK);
	}
	
	@GetMapping(value="/instructors")
	public ResponseEntity viewProfessors() {
		List<Professor>professors=adminService.viewProfessors();
		return new ResponseEntity(professors,HttpStatus.OK);
	}
	
	/**
	 * {
        "userId": "bob123@gmail.com",
        "password": "*********",
        "gender": "MALE",
        "role": "PROFESSOR",
        "address": "delhi",
        "name": "bob",
        "professorId": null,
        "department": "DS",
        "designation": "Asst. Prof"
    	}
	 */
	@PostMapping(value="/addProfessor")
	public ResponseEntity addProfessor(@RequestBody Professor professor) {
		try {
			adminService.addProfessor(professor);
			return new ResponseEntity(professor,HttpStatus.OK);
		} catch (ProfessorNotAddedException | UserIdAlreadyInUseException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value="/dropProfessor/{id}")
	public ResponseEntity dropProfessor(@PathVariable("id") String professorId) {
		try {
			adminService.dropProfessor(professorId);
			return new ResponseEntity("Professor Dropped with Id: "+professorId,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="assignCourse/{cId}/{pId}")
	public ResponseEntity assignCourseToProfessor(@PathVariable("cId")String courseId, @PathVariable("pId")String professorId) {
		try {
			adminService.assignCourse(courseId, professorId);
			return new ResponseEntity("Course :"+courseId+" assigned to Professor: "+professorId,HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (CourseNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/generateReportCard/{id}")
	public ResponseEntity generateReportCard(@PathVariable("id")String studentId) 
	{
		GradeCard grade_card=null;
		adminService.setGeneratedReportCardTrue(studentId);
		try {
			grade_card = registrationService.viewGradeCard(studentId);
			return new ResponseEntity(grade_card,HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
	

}
