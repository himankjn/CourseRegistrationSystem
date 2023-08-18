/**
 * 
 */
package com.wibmo.client;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.business.AdminServiceInterface;
import com.wibmo.business.NotificationServiceImpl;
import com.wibmo.business.NotificationServiceInterface;
import com.wibmo.business.RegistrationServiceImpl;
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
import com.wibmo.business.AdminServiceImpl;


/**
 * @author Shanmukh
 *
 */
public class CRSAdminMenu {
	
	Logger logger= Logger.getLogger(CRSAdminMenu.class);
	AdminServiceInterface adminService = AdminServiceImpl.getInstance();
	Scanner in = new Scanner(System.in);
	NotificationServiceInterface notificationInterface=NotificationServiceImpl.getInstance();
	RegistrationServiceInterface registrationServiceInterface = RegistrationServiceImpl.getInstance();
	
	/**
	 * Method to Create Admin Menu
	 */
	public void createMenu(){
		
		while(CRSApplicationClient.loggedin) {
			logger.info("|====================Admin Menu===================");
			logger.info("|            1. View course in catalog          |");
			logger.info("|            2. Add Course to catalog           |");
			logger.info("|            3. Delete Course from catalog      |");
			logger.info("|            4. Approve Students                |");
			logger.info("|            5. View Pending Student Registration Approvals          |");
			logger.info("|            6. Add Professor                   |");
			logger.info("|            7. Assign Professor To Courses     |");
			logger.info("|            8. Generate Report Card            |");
			logger.info("|            9. Logout                          |");
			logger.info("|==================================================");
			
			int choice = in.nextInt();
			
			switch(choice) {
			case 1:
				viewCoursesInCatalogue();
				break;
				
			case 2:
				addCourseToCatalogue();
				break;
				
			case 3:
				removeCourse();
				break;
				
			case 4:
				approveStudent();
				break;
			
			case 5:
				viewPendingAdmissions();
				break;
			
			case 6:
				addProfessor();
				break;
			
			case 7:
				assignCourseToProfessor();
				break;
				
			case 8:
				generateReportCard();
				break;
			
			case 9:
				CRSApplicationClient.loggedin = false;
				return;

			default:
				logger.info("***** Wrong Choice *****");
			}
		}
	}
	
	private void generateReportCard() 
	{
		
		List<Grade> grade_card=null;
		boolean isReportGenerated = true;
		
		Scanner in = new Scanner(System.in);
		
		logger.info("\nEnter the StudentId for report card generation : ");
		String studentId = in.next();
		
		adminService.setGeneratedReportCardTrue(studentId);
		if(isReportGenerated) {
			try {
				grade_card = registrationServiceInterface.viewGradeCard(studentId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(String.format("%-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "GRADE"));
			
			if(grade_card.isEmpty())
			{
				logger.info("You haven't registered for any course");
				return;
			}
			
			for(Grade obj : grade_card)
			{
				logger.info(String.format("%-20s %-20s %-20s",obj.getCrsCode(), obj.getCrsName(),obj.getGrade()));
			}
		}
		else
			logger.info("Report card not yet generated");
		
		
	}

	

	/**
	 * Method to assign Course to a Professor
	 */
	private void assignCourseToProfessor() {
		List<Professor> professorList= adminService.viewProfessors();
		logger.info("*************************** Professor *************************** ");
		logger.info(String.format("%20s | %20s | %20s ", "ProfessorId", "Name", "Designation"));
		for(Professor professor : professorList) {
			logger.info(String.format("%20s | %20s | %20s ", professor.getUserId(), professor.getName(), professor.getDesignation()));
		}
		
		
		logger.info("\n\n");
		List<Course> courseList= adminService.viewCourses();
		logger.info("**************** Course ****************");
		logger.info(String.format("%20s | %20s | %20s", "CourseId", "CourseName", "ProfessorId"));
		for(Course course : courseList) {
			logger.info(String.format("%20s | %20s | %20s", course.getCourseId(), course.getCourseName(), course.getInstructorId()));
		}
		
		logger.info("Enter Course Code:");
		String courseId = in.next();
		
		viewCourseRequests(courseId);
		
		logger.info("Enter Professor's User Id:");
		String userId = in.next();
		try {
			adminService.assignCourse(courseId, userId);
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * Method to add Professor to DB
	 */
	private void addProfessor() {
		
		in.nextLine();
		logger.info("Enter User Id:");
		String userId = in.nextLine();
		Professor professor = new Professor(userId);
		
		professor.setProfessorId(userId);
		
		logger.info("Enter Professor Name:");
		String professorName = in.nextLine();
		professor.setName(professorName);
		
		logger.info("Enter Department:");
		String department = in.nextLine();
		professor.setDepartment(department);
		
		logger.info("Enter Designation:");
		String designation = in.nextLine();
		professor.setDesignation(designation);
		
		logger.info("Enter Password:");
		String password = in.nextLine();
		professor.setPassword(password);
		
		logger.info("Enter GenderConstant: \t 1: Male \t 2.Female \t 3.Other ");
		int gender = in.nextInt();
		
		if(gender==1)
			professor.setGender(GenderConstant.MALE);
		else if(gender==2)
			professor.setGender(GenderConstant.FEMALE);
		else if(gender==3)
			professor.setGender(GenderConstant.OTHER);
		
		logger.info("Enter Address:");
		String address = in.next();
		professor.setAddress(address);
		
		professor.setRole(RoleConstant.PROFESSOR);
		
		try {
			adminService.addProfessor(professor);
		} catch (ProfessorNotAddedException | UserIdAlreadyInUseException e) {
			logger.info(e.getMessage());
		}

	}

	/**
	 * Method to view Students who are yet to be approved
	 * @return List of Students whose admissions are pending
	 */
	private List<Student> viewPendingAdmissions() {
		
		List<Student> pendingStudentsList= adminService.viewPendingAdmissions();
		if(pendingStudentsList.size() == 0) {
			logger.info("No students pending approvals");
			return pendingStudentsList;
		}
		logger.info(String.format("%20s | %20s | %20s", "StudentId", "Name", "GenderConstant"));
		for(Student student : pendingStudentsList) {
			logger.info(String.format("%20s | %20s | %20s", student.getStudentId(), student.getName(), student.getGender().toString()));
		}
		return pendingStudentsList;
	}

	/**
	 * Method to approve a Student using Student's ID
	 */
	private void approveStudent() {
		
		List<Student> studentList = viewPendingAdmissions();
		if(studentList.size() == 0) {
			logger.info("No pending admissions!");
			return;
		}
		
		logger.info("1.Approve Single Student\n2. Approve All Students");
		int approveChoice= in.nextInt();
		if(approveChoice==1) {
			logger.info("Enter Student's ID for a:");
			String studentUserIdApproval = in.next();
			try {
				adminService.approveSingleStudent(studentUserIdApproval, studentList);
				logger.info("\nStudent Id : " +studentUserIdApproval+ " has been approved\n");
				//send notification from system
				notificationInterface.sendNotification(NotificationTypeConstant.REGISTRATION, studentUserIdApproval, null,0);
		
			} catch (StudentNotFoundForApprovalException e) {
				logger.info(e.getMessage());
			}
		}
		else if(approveChoice==2) {
			try {
				adminService.approveAllStudents(studentList);
				logger.info("All students approved!");
				//notify
			}
			catch(Exception e){
				logger.info(e.getMessage());
			}
		}
		
		
		
		
	
		
	}

	/**
	 * Method to delete Course from catalogue
	 * @throws CourseNotFoundException 
	 */
	private void removeCourse() {
		
		logger.info("Enter Course Code:");
		String courseId = in.next();
		
		try {
			adminService.removeCourse(courseId);
			logger.info("\nCourse Deleted : "+courseId+"\n");
		} catch (CourseNotFoundException e) {
			
			logger.info(e.getMessage());
		}
		catch (CourseNotDeletedException e) {
			
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * Method to add Course to catalogue
	 * @throws CourseExistsAlreadyException 
	 */
	private void addCourseToCatalogue() {

		in.nextLine();
		logger.info("Course Id:");
		String courseId = in.nextLine();
		
		logger.info("Enter Course Name:");
		String courseName = in.nextLine();
		
		Course course = new Course(courseId, courseName,"", 10);
		course.setCourseId(courseId);
		course.setCourseName(courseName);
		course.setSeats(10);
		
		try {
		adminService.addCourse(course);		
		}
		catch (CourseExistsAlreadyException e) {
			logger.info("Course already existed "+e.getMessage());
		}

	}
	
	/**
	 * Method to display courses in catalogue
	 * @return List of courses in catalogue
	 */
	private List<Course> viewCoursesInCatalogue() {
		List<Course> courseList = adminService.viewCourses();
		if(courseList.size() == 0) {
			logger.info("Nothing present in the catalogue!");
			return courseList;
		}
		logger.info(String.format("%20s | %20s | %20s","COURSE CODE", "COURSE NAME", "INSTRUCTOR"));
		for(Course course : courseList) {
			logger.info(String.format("%20s | %20s | %20s", course.getCourseId(), course.getCourseName(), course.getInstructorId()));
		}
		return courseList;
	}
	
	void viewCourseRequests(String courseId){
		List<String> professorIds= adminService.viewProfCourseRequests(courseId);
		logger.info("Professors who have requested "+courseId+" are: ");
		for(String professorID: professorIds) {
			System.out.print(professorID+ "  ");
		}
		logger.info();
	}

}
