/**
 * 
 */
package com.wibmo.client;

import com.wibmo.business.RegistrationServiceInterface;
import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.bean.GradeCard;
import com.wibmo.bean.RegisteredCourse;

import com.wibmo.business.NotificationServiceImpl;
import com.wibmo.business.NotificationServiceInterface;
import com.wibmo.business.RegistrationServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * @author himank
 *
 */
public class CRSStudentMenu {
	Logger logger = Logger.getLogger(CRSStudentMenu.class);
	Scanner sc = new Scanner(System.in);
	RegistrationServiceInterface registrationServiceInterface = RegistrationServiceImpl.getInstance();
	NotificationServiceInterface notificationInterface = NotificationServiceImpl.getInstance();
	private boolean is_registered;
	
	
	public void create_menu(String studentId) {
		
		try {
			is_registered = getRegistrationStatus(studentId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(CRSApplicationClient.loggedin) {
				logger.info("===========Student Menu===========");
				logger.info("==================================");
				logger.info("1. Course Registration");
				logger.info("2. Add Course");
				logger.info("3. Drop Course");
				logger.info("4. View Available Courses");
				logger.info("5. View Registered Courses");
				logger.info("6. View grade card");
				logger.info("7. Make Payment");
				logger.info("8. Logout");
				logger.info("==================================");
			
				int choice = sc.nextInt();
			
				switch (choice) {
				
				case 1: 
					registerCourses(studentId);
					break;
					
				case 2:
					addCourse(studentId);
					break;
					
				case 3:
					dropCourse(studentId);
					break;
					
				case 4:
					viewAvailableCourse(studentId);
					break;
					
				case 5:
					viewRegisteredCourse(studentId);
					break;
					
				case 6:
					viewGradeCard(studentId);
					break;
					
				case 7:
					make_payment(studentId);
					break;
					
				case 8:
					CRSApplicationClient.loggedin = false;
					break;			
					
				default:
					logger.info("Incorrect Choice!");
		
		
			}
			
		}
		
	}






private void registerCourses(String studentId)
{
	
	
	if(is_registered)
	{
		logger.info(" Registration is already completed");
		return;
	}
	
	for(int i=1;i<=6;i++)
	{
		List<Course> courseList=viewAvailableCourse(studentId);
		
		if(courseList==null)
			return;
		
		logger.info("Enter Course Code " + (i)+": ");
		String courseCode = sc.next();
		
		try {
			if(registrationServiceInterface.addCourse(courseCode,studentId))
			{
				logger.info("Course " + courseCode + " registered sucessfully.");
			}
			else
			{
				logger.info(" You have already registered for Course : " + courseCode);
			}
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseLimitExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeatNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	logger.info("Registration Successful");
	
	try {
		registrationServiceInterface.setRegistrationStatus(studentId);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    is_registered = true;
    
//    try 
//    {
//		registrationInterface.setRegistrationStatus(studentId);
//	} 
//    catch (SQLException e) 
//    {
//    	logger.info(e.getMessage());
//	}
}


private void addCourse(String studentId) {
	if(is_registered)
	{
		
		logger.info("Enter Course Id : " );
		String courseCode = sc.next();
		try {
			if(registrationServiceInterface.addCourse(courseCode, studentId))
			{
				logger.info(" Successfully registered for Course : " + courseCode);
			}
			else
			{
				logger.info(" Registered for Course : " + courseCode);
			}
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseLimitExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeatNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else 
	{
		logger.info("Please complete registration first!");
	}

}

/**
 * Method to check if student is already registered
 * @param studentId
 * @return Registration Status
 * @throws SQLException 
 */
private boolean getRegistrationStatus(String studentId) throws SQLException
{
	return registrationServiceInterface.getRegistrationStatus(studentId);
}


/**
 * Drop Course
 * @param studentId
 */
private void dropCourse(String studentId) {
	if(is_registered)
	{
		List<Course> registeredCourseList=viewRegisteredCourse(studentId);
		
		if(registeredCourseList==null)
			return;
		
		logger.info("Enter the Course Code : ");
		String courseCode = sc.next();
		
		try {
			registrationServiceInterface.dropCourse(courseCode, studentId,registeredCourseList);
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Successfully dropped Course : " + courseCode);
	}
	else
	{
		logger.info("Please complete registration");
	}
}


/**
 * View all available Courses 
 * @param studentId
 * @return List of available Courses 
 */
private List<Course> viewAvailableCourse(String studentId){
	List<Course> course_available=null;
	try {
		course_available = registrationServiceInterface.viewCourses(studentId);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		logger.error(e.getMessage());
	}


	if(course_available.isEmpty())
	{
		logger.info("NO COURSE AVAILABLE");
		return null;
	}
	

	logger.info(String.format("%-20s %-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "PROFESSOR", "SEATS"));
	for(Course obj : course_available)
	{
		logger.info(String.format("%-20s %-20s %-20s %-20s",obj.getCourseId(), obj.getCourseName(),obj.getInstructorId(), obj.getSeats()));
	}
	
	return course_available;
}


/**
 * View Registered Courses
 * @param studentId
 * @return List of Registered Courses
 */
private List<Course> viewRegisteredCourse(String studentId){
	List<Course> course_registered=null;
	try {
		course_registered = registrationServiceInterface.viewRegisteredCourses(studentId);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(course_registered.isEmpty())
	{
		logger.info("You haven't registered for any course");
		return null;
	}
	
	logger.info(String.format("%-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "PROFESSOR"));
	
	for(Course obj : course_registered)
	{
		 
		
		logger.info(String.format("%-20s %-20s %-20s ",obj.getCourseId(), obj.getCourseName(),obj.getInstructorId()));
	}
	
	return course_registered;
}

/**
 * View grade card for particular student  
 * @param studentId
 */
private void viewGradeCard(String studentId) {
	GradeCard grade_card=null;
	boolean isReportGenerated = false;
	
	try 
	{
		isReportGenerated = registrationServiceInterface.isReportGenerated(studentId);
		if(isReportGenerated) {
			grade_card = registrationServiceInterface.viewGradeCard(studentId);
			logger.info(String.format("%-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "GRADE"));
			
			if(grade_card.getReg_list().isEmpty())
			{
				logger.info("You haven't registered for any course");
				return;
			}
			
			for(RegisteredCourse obj : grade_card.getReg_list())
			{
				logger.info(String.format("%-20s %-20s %-20s",obj.getCourse().getCourseId(), obj.getCourse().getCourseName(),obj.getGrade()));
			}
			
			logger.info("CGPA:"+ grade_card.getCgpa());
		}
		else
			logger.info("Report card not yet generated");
	} 
	catch (SQLException e) 
	{

		logger.info(e.getMessage());
	}
	
	
}

private void make_payment(String studentId)
{
	
	double fee = 1000.0;
	boolean isreg = false;
	boolean ispaid = false;
	try
	{
		isreg = registrationServiceInterface.getRegistrationStatus(studentId);
		ispaid = registrationServiceInterface.getPaymentStatus(studentId);
//		fee=registrationInterface.calculateFee(studentId);
	} 
	catch (SQLException e) 
	{

        logger.info(e.getMessage());
	}

	
	if(!isreg)
	{
		logger.info("You have not registered yet");
	}
	else if(isreg && !ispaid)
	{
		
		logger.info("Your total fee  = " + fee);
		logger.info("Want to continue Fee Payment(y/n)");
		String ch = sc.next();
		if(ch.equals("y"))
		{
			logger.info("Select Mode of Payment:");
			
			int index = 1;
			for(PaymentModeConstant mode : PaymentModeConstant.values())
			{
				logger.info(index + " " + mode);
				index = index + 1;
			}
			
			PaymentModeConstant mode = PaymentModeConstant.getPaymentMode(sc.nextInt());
			
			if(mode == null)
				logger.info("Invalid Input");
			else
			{
				try 
				{
					notificationInterface.sendNotification(NotificationTypeConstant.PAYED, studentId, mode, fee);
					logger.info("Payment Successful by StudentId :" + studentId);
					registrationServiceInterface.setPaymentStatus(studentId);				
				}
				catch (Exception e) 
				{

		            logger.info(e.getMessage());
				}
			}
			
			
			
				
		}
		
	}
	
	else
	{
		logger.info("You have already paid the fees");
	}
	
}
}