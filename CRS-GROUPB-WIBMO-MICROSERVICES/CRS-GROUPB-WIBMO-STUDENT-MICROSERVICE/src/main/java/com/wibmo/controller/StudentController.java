package com.wibmo.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.entity.Course;
import com.wibmo.entity.GradeCard;
import com.wibmo.entity.Payment;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseAlreadyRegisteredException;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotApplicableForSemesterException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.NotificationServiceInterface;
import com.wibmo.service.RegistrationServiceInterface;
import com.wibmo.service.StudentServiceInterface;
import com.wibmo.service.UserServiceInterface;

import net.minidev.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@PreAuthorize("hasAuthority('STUDENT')")
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger= LogManager.getLogger(StudentController.class);
	
	
	@Autowired
	private RegistrationServiceInterface registrationService;
	
	@Autowired
	private NotificationServiceInterface notificationService;
	
	@Autowired
	private UserServiceInterface userService;
	
	/**
	 * update password of User
	 */
	
	@RequestMapping(value="updatePassword/{id}/{pass}",method=RequestMethod.PUT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	 * Method for course regisration of student for the semester
	 * @param studentId
	 * @param courseList
	 * @return ResponseEntity
	 * @throws CourseAlreadyRegisteredException 
	 */
	@RequestMapping(value="/semRegistration/{sId}/{sem}",method=RequestMethod.POST)
	private ResponseEntity registerCourses(@PathVariable("sId") String studentId,@PathVariable("sem")int sem, @RequestBody List<Course> courseList) throws CourseAlreadyRegisteredException
	{
		List<Course> registeredCourses=new ArrayList<Course>();
		boolean isRegistered;
		try {
			isRegistered = registrationService.getRegistrationStatus(studentId);
		} catch (SQLException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(isRegistered)
		{
			return new ResponseEntity("Already registered!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		registrationService.setSemForStudent(studentId,sem);
		int regCount=0;
		for(Course course: courseList)
		{
			if(regCount==4)break;
			String courseCode= course.getCourseId();
			try {
				if(registrationService.addCourse(courseCode,studentId))
				{
					logger.info("Course " + courseCode + " registered sucessfully.");
					registeredCourses.add(course);
					regCount++;
				}
				else
				{
					logger.info(" You have already registered for Course : " + courseCode);
				}
			} catch (CourseNotFoundException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (CourseNotApplicableForSemesterException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			}catch (CourseLimitExceededException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (SeatNotAvailableException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (CourseAlreadyRegisteredException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			}catch (SQLException e) {
				return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Registration Successful");
		
		try {
			registrationService.setRegistrationStatus(studentId);
			return new ResponseEntity(registeredCourses,HttpStatus.OK);
			//notificationService.sendNotification(NotificationTypeConstant.REGISTRATION, studentId, null, 0);
		}catch(Exception e) {
			return new ResponseEntity("Unsuccessful Registration!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
    /**
     * Adds the course to the student for Registration
     * @param studentId
     * @param courseId
     * @return
     * @throws CourseNotApplicableForSemesterException 
     */
	@RequestMapping(method = RequestMethod.POST,value = "/addCourse/{studentId}/{courseId}")
	public ResponseEntity addCourseToStudent(@PathVariable("studentId") String studentId,@PathVariable("courseId") String courseId) throws CourseNotApplicableForSemesterException {
        logger.info("Adding and trying to register a course for a student");
        try{
            registrationService.addCourse(courseId,studentId);
            return new ResponseEntity("Course: "+courseId+" added for student: "+studentId,HttpStatus.OK);
        } catch(CourseNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(CourseAlreadyRegisteredException e) {
        	return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(CourseLimitExceededException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(SeatNotAvailableException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(CourseNotApplicableForSemesterException e){
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
            return new ResponseEntity("Course: "+courseId+" dropped by student: "+studentId,HttpStatus.OK);
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
    @RequestMapping(method = RequestMethod.GET, value = "/registeredCourses/{studentId}")
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
    @RequestMapping(method = RequestMethod.GET,value = "/availableCourses/{studentId}")
	public ResponseEntity viewAvailableCourses(@PathVariable("studentId") String studentId) {
		logger.info("Retrieving Available courses that student: "+studentId+" can add");
        try{
            List<Course> courseList = registrationService.viewAvailableCourses(studentId);
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
    @RequestMapping(method = RequestMethod.GET,value = "/gradeCard/{studentId}")
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

    @RequestMapping(consumes=MediaType.APPLICATION_JSON ,method = RequestMethod.PUT,value = "/payFee/{studentId}")
	public ResponseEntity payTheFee(@RequestBody String jsonBody,@PathVariable("studentId") String studentId) throws JsonMappingException, JsonProcessingException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonNode jsonNode = objectMapper.readTree(jsonBody);
		logger.info("Fee is about to be paid by the  student: "+studentId);
        try{
        	double feeToBePaid = registrationService.calculateFee(studentId);
        	String modeOfPayment = jsonNode.get("paymentMode").asText();
        	PaymentModeConstant paymentMode;
        	switch(modeOfPayment) {
        		case "NET_BANKING": paymentMode = PaymentModeConstant.NET_BANKING;
        		break;
        		case "DEBIT_CARD": paymentMode = PaymentModeConstant.DEBIT_CARD;
        		break;
        		case "CREDIT_CARD": paymentMode = PaymentModeConstant.CREDIT_CARD;
        		break;
        		default: paymentMode = PaymentModeConstant.DEBIT_CARD;
        	}
        	notificationService.sendNotification(NotificationTypeConstant.PAYED,studentId, paymentMode, feeToBePaid);
        	registrationService.setPaymentStatus(studentId);
            return new ResponseEntity("Fee Has been successfully paid by student: "+studentId+" through "+modeOfPayment,HttpStatus.OK);
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
