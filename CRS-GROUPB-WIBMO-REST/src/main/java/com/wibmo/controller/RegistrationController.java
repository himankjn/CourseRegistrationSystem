package com.wibmo.controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.text.DefaultStyledDocument.ElementSpec;
import javax.ws.rs.core.MediaType;

import com.wibmo.bean.Course;
import com.wibmo.bean.GradeCard;
import com.wibmo.business.RegistrationServiceInterface;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {
    private static final Logger logger= LogManager.getLogger(AdminController.class);
	
	
	@Autowired
	private RegistrationServiceInterface registrationService;

    /**
     * Adds the course to the student for Registration
     * @param studentId
     * @param courseId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/addCourse/{studentId}/{courseId}")
	public ResponseEntity addCourseToStudent(@PathVariable("studentId") String studentId,@PathVariable("courseId") String courseId) {
        logger.info("Adding and trying to register a course for a student");
        try{
            registrationService.addCourse(courseId,studentId);
            return new ResponseEntity("Course: "+courseId+" got added to student: "+studentId,HttpStatus.OK);
        } catch(CourseNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(CourseLimitExceededException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(SeatNotAvailableException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(SQLException e) {
        	return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

    /**
     * Drops the course for the student which were registered
     * @param studentId
     * @param courseId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/dropCourse/{studentId}/{courseId}")
	public ResponseEntity dropCourseToStudent(@PathVariable("studentId") String studentId,@PathVariable("courseId") String courseId) {
		logger.info("Dropping and trying to de-register a course for a student");
		try{
            registrationService.dropCourse(courseId,studentId,registrationService.viewRegisteredCourses(studentId));
            return new ResponseEntity("Course: "+courseId+" got dropped to student: "+studentId,HttpStatus.OK);
        } catch(CourseNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(SQLException e) {
        	return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
    
    /**
     * Sets the Registration Status of the student as done
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/registration/{studentId}")
    public ResponseEntity SetRegistrationStatusOfStudent(@PathVariable("studentId") String studentId) {
    	logger.info("Setting registration status to true as registration is done of student: "+studentId);
    	try {
    		registrationService.setRegistrationStatus(studentId);
    		return new ResponseEntity("Registration Status has been successfully set as done for student: "+studentId,HttpStatus.OK);
    	} catch(SQLException e) {
        	return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Retrieves the Registration Status of a Student as done or not done
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/registration/{studentId}")
    public ResponseEntity GetRegistrationStatusOfStudent(@PathVariable("studentId") String studentId) {
    	logger.info("Fetching the Registration Status of student: "+studentId);
    	try {
    		boolean registrationStatus = registrationService.getRegistrationStatus(studentId);
    		return new ResponseEntity("Registration Status is: "+registrationStatus+ " for student: "+studentId,HttpStatus.OK);
    	} catch(SQLException e) {
        	return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Sets the Payment Status of a student as Done
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/payment/{studentId}")
    public ResponseEntity SetPaymentStatusOfStudent(@PathVariable("studentId") String studentId) {
    	logger.info("Setting Payment status to true as Payment is done for student: "+studentId);
    	try {
    		registrationService.setPaymentStatus(studentId);
    		return new ResponseEntity("Payment Status has been successfully set as done for student: "+studentId,HttpStatus.OK);
    	} catch(SQLException e) {
        	return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Retrieves the payment Status of a student as done or not done
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/payment/{studentId}")
    public ResponseEntity GetPaymentStatusOfStudent(@PathVariable("studentId") String studentId) {
    	logger.info("Getting Payment Status of Student "+studentId);
    	try {
    		boolean registrationStatus = registrationService.getPaymentStatus(studentId);
    		return new ResponseEntity("Payment Status is: "+registrationStatus+ " for student: "+studentId,HttpStatus.OK);
    	} catch(SQLException e) {
        	return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Fetches all the courses which are registered by the student
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/RegisteredCourses/{studentId}")
	public ResponseEntity viewRegisteredCourses(@PathVariable("studentId") String studentId) {
		logger.info("Retrieving Registered courses for student: "+studentId);
        try{
            List<Course> courseList = registrationService.viewRegisteredCourses(studentId);
            return new ResponseEntity(courseList,HttpStatus.OK);
        } catch(SQLException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }		
		
	}

    /**
     * Fetches all the plausible courses which the student can register for
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/AvailableCourses/{studentId}")
	public ResponseEntity viewAvailableCourses(@PathVariable("studentId") String studentId) {
		logger.info("Retrieving Available courses that student: "+studentId+" can add");
        try{
            List<Course> courseList = registrationService.viewCourses(studentId);
            return new ResponseEntity(courseList,HttpStatus.OK);
        } catch(SQLException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }		
		
	}

    /**
     * Fetches the GradeCard of a student
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/GradeCard/{studentId}")
	public ResponseEntity viewGradeCard(@PathVariable("studentId") String studentId) {
		logger.info("Displaying the Grade Card of student: "+studentId);
        try{
            GradeCard gradecard = registrationService.viewGradeCard(studentId);
            return new ResponseEntity(gradecard,HttpStatus.OK);
        } catch(SQLException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }		
		
	}
    
    /**
     * Calculate the Amount to be paid by the student as Fee.
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/FeeNeeded/{studentId}")
	public ResponseEntity FeeNeededToPay(@PathVariable("studentId") String studentId) {
		logger.info("Dispalying Amount to be paid as Fee by the  student: "+studentId);
        try{
            double feeNeeded = registrationService.calculateFee(studentId);
            return new ResponseEntity("The Fee Needed to be paid by student: "+studentId+" is "+feeNeeded,HttpStatus.OK);
        } catch(SQLException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }		
		
	}

    /**
     * Shows if Report Card of a student has been generated already
     * @param studentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/isReportGenerated/{studentId}")
	public ResponseEntity isReportGenerated(@PathVariable("studentId") String studentId) {
		logger.info("Checking if the ReportCard is Generated for student: "+studentId);
        try{
            boolean reportGenerated = registrationService.isReportGenerated(studentId);
            if(reportGenerated){
                return new ResponseEntity("The Report has been generated for the student: "+studentId, HttpStatus.OK);
            }else{
                return new ResponseEntity("The Report has not been generated for the student: "+studentId, HttpStatus.OK);
            }
        } catch(SQLException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }		
		
	}
}
