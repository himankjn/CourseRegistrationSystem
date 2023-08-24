/**
 * 
 */
package com.wibmo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.business.ProfessorServiceInterface;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.bean.Student;

import com.wibmo.business.ProfessorServiceImpl;


/**
 * @author himank
 *
 */
public class CRSProfessorMenu {
	Logger logger = Logger.getLogger(CRSProfessorMenu.class);
	ProfessorServiceInterface professorInterface = ProfessorServiceImpl.getInstance();

	/**
	 * @param profID
	 */
	public void createMenu(String profID) {
		Scanner in = new Scanner(System.in);
		
		int input;
		while (true) {
			logger.info("================================");	   
			logger.info("========Professor Menu========");
			logger.info("================================");
			logger.info("1. View Assigned Courses");
			logger.info("2. View Enrolled Students");
			logger.info("3. Add Grades");
			logger.info("4. Request for course assignmnet");
			logger.info("5. Logout");
			logger.info("================================");
			logger.info("Choose From Menu: ");
			
			input = in.nextInt();
			String courseId;
			switch (input) {
			case 1:
				logger.info("PROF ID: "+profID);
				getAssignedCourses(profID);
				break;
			case 2:
				getAssignedCourses(profID);
				logger.info("Enter the courseID for veiwing enrolled Students: ");
				in.nextLine();
				courseId= in.nextLine();
				viewEnrolledStudents(courseId);
				break;
			case 3:
				addGrade(profID);
				break;
			case 4:
				getUnassignedCourses();
				logger.info("Enter the courseID you want to request:");
				in.nextLine();
				courseId= in.nextLine();
				requestCourseAssignment(profID,courseId);
				break;
			case 5:
				CRSApplicationClient.loggedin = false;
				return;
			default:
				logger.info("Please select appropriate option...");
			}
		}
	}
	
	
	public void getUnassignedCourses() {
		try {
			List<Course> unassignedCourses= professorInterface.getUnassignedCourses();
			logger.info(String.format("|      %20s       |           %20s       |", "COURSE Id","COURSE NAME" ));
			for(Course course: unassignedCourses) {
				logger.info(String.format("|      %20s       |           %20s       |",course.getCourseId(), course.getCourseName()));
			}		
		} catch(Exception ex) {
			logger.info("Something went wrong!"+ex.getMessage());
		}
	}
	
	public void requestCourseAssignment(String profID,String courseId) {
		if(professorInterface.requestCourseAssignment(profID, courseId)) {
			logger.info("Request sent to admin for approval!");
		}
		else {
			logger.info("Request unsuccessful! Something went wrong. Please contact admin.");
		}
		
	}
	
	public void viewEnrolledStudents(String profID) {
		try {
			List<EnrolledStudent> enrolledStudents = new ArrayList<EnrolledStudent>();
			enrolledStudents = professorInterface.viewEnrolledStudents(profID);
			if(enrolledStudents.isEmpty()) {
				logger.info("No students enrolled for this course!");
				return;
			}
			logger.info(String.format("%20s %20s %20s","COURSE ID","COURSE NAME","Student" ));
			for (EnrolledStudent obj: enrolledStudents) {
				logger.info(String.format("%20s %20s %20s",obj.getCourseCode(), obj.getCourseName(),obj.getStudentId()));
			}
			
		} catch(Exception ex) {
			logger.info(ex.getMessage()+"Something went wrong, please try again later!");
		}
	}
	
	public void getAssignedCourses(String profId) {
		try {
			List<Course> coursesEnrolled = professorInterface.viewAssignedCourses(profId);
			logger.info(String.format("|      %20s       |           %20s       |", "COURSE ID","COURSE NAME" ));
			for(Course course: coursesEnrolled) {
				logger.info(String.format("|      %20s       |           %20s       |",course.getCourseId(), course.getCourseName()));
			}		
		} catch(Exception ex) {
			logger.info("Something went wrong!"+ex.getMessage());
		}
	}
	
	public void addGrade(String profId) {	
		Scanner in = new Scanner(System.in);
		String courseId, grade, studentId;
		try {
			logger.info("==============Add Grade==============");
			getAssignedCourses(profId);	
			System.out.printf("Enter COURSE ID: ");
			courseId = in.nextLine();
			viewEnrolledStudents(courseId);
			System.out.printf("Enter student id: ");
			studentId = in.nextLine();
			logger.info("Enter grade: ");
			grade = in.nextLine();
			professorInterface.submitGrade(studentId, courseId, grade);
			logger.info("grade added successfully for "+studentId);
		} catch(Exception ex) {
			logger.info("GradeConstant cannot be added for"+ex.getStackTrace());
			
		} 
	}

}
