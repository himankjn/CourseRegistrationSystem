/**
 * 
 */
package com.wibmo.client;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

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
	AdminServiceInterface adminService = AdminServiceImpl.getInstance();
	Scanner in = new Scanner(System.in);
	NotificationServiceInterface notificationInterface=NotificationServiceImpl.getInstance();
	RegistrationServiceInterface registrationServiceInterface = RegistrationServiceImpl.getInstance();
	
	/**
	 * Method to Create Admin Menu
	 */
	public void createMenu(){
		
		while(CRSApplicationClient.loggedin) {
			System.out.println("====================Admin Menu===================");
			System.out.println("|            1. View course in catalog          |");
			System.out.println("|            2. Add Course to catalog           |");
			System.out.println("|            3. Delete Course from catalog      |");
			System.out.println("|            4. Approve Students                |");
			System.out.println("|            5. View Pending Approvals          |");
			System.out.println("|            6. Add Professor                   |");
			System.out.println("|            7. Assign Professor To Courses     |");
			System.out.println("|            8. Generate Report Card            |");
			System.out.println("|            9. Logout                          |");
			System.out.println("==================================================");
			
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
				System.out.println("***** Wrong Choice *****");
			}
		}
	}
	
	private void generateReportCard() 
	{
		
		List<Grade> grade_card=null;
		boolean isReportGenerated = true;
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("\nEnter the StudentId for report card generation : ");
		String studentId = in.next();
		
		adminService.setGeneratedReportCardTrue(studentId);
		if(isReportGenerated) {
			try {
				grade_card = registrationServiceInterface.viewGradeCard(studentId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(String.format("%-20s %-20s %-20s","COURSE CODE", "COURSE NAME", "GRADE"));
			
			if(grade_card.isEmpty())
			{
				System.out.println("You haven't registered for any course");
				return;
			}
			
			for(Grade obj : grade_card)
			{
				System.out.println(String.format("%-20s %-20s %-20s",obj.getCrsCode(), obj.getCrsName(),obj.getGrade()));
			}
		}
		else
			System.out.println("Report card not yet generated");
		
		
	}

	

	/**
	 * Method to assign Course to a Professor
	 */
	private void assignCourseToProfessor() {
		List<Professor> professorList= adminService.viewProfessors();
		System.out.println("*************************** Professor *************************** ");
		System.out.println(String.format("%20s | %20s | %20s ", "ProfessorId", "Name", "Designation"));
		for(Professor professor : professorList) {
			System.out.println(String.format("%20s | %20s | %20s ", professor.getUserId(), professor.getName(), professor.getDesignation()));
		}
		
		
		System.out.println("\n\n");
		List<Course> courseList= adminService.viewCourses();
		System.out.println("**************** Course ****************");
		System.out.println(String.format("%20s | %20s | %20s", "courseId", "CourseName", "ProfessorId"));
		for(Course course : courseList) {
			System.out.println(String.format("%20s | %20s | %20s", course.getCourseId(), course.getCourseName(), course.getInstructorId()));
		}
		
		System.out.println("Enter Course Code:");
		String courseId = in.next();
		
		System.out.println("Enter Professor's User Id:");
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
		System.out.println("Enter User Id:");
		String userId = in.nextLine();
		Professor professor = new Professor(userId);
		
		professor.setProfessorId(userId);
		
		System.out.println("Enter Professor Name:");
		String professorName = in.nextLine();
		professor.setName(professorName);
		
		System.out.println("Enter Department:");
		String department = in.nextLine();
		professor.setDepartment(department);
		
		System.out.println("Enter Designation:");
		String designation = in.nextLine();
		professor.setDesignation(designation);
		
		System.out.println("Enter Password:");
		String password = in.nextLine();
		professor.setPassword(password);
		
		System.out.println("Enter GenderConstant: \t 1: Male \t 2.Female \t 3.Other ");
		int gender = in.nextInt();
		
		if(gender==1)
			professor.setGender(GenderConstant.MALE);
		else if(gender==2)
			professor.setGender(GenderConstant.FEMALE);
		else if(gender==3)
			professor.setGender(GenderConstant.OTHER);
		
		System.out.println("Enter Address:");
		String address = in.next();
		professor.setAddress(address);
		
		professor.setRole(RoleConstant.PROFESSOR);
		
		try {
			adminService.addProfessor(professor);
		} catch (ProfessorNotAddedException | UserIdAlreadyInUseException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Method to view Students who are yet to be approved
	 * @return List of Students whose admissions are pending
	 */
	private List<Student> viewPendingAdmissions() {
		
		List<Student> pendingStudentsList= adminService.viewPendingAdmissions();
		if(pendingStudentsList.size() == 0) {
			System.out.println("No students pending approvals");
			return pendingStudentsList;
		}
		System.out.println(String.format("%20s | %20s | %20s", "StudentId", "Name", "GenderConstant"));
		for(Student student : pendingStudentsList) {
			System.out.println(String.format("%20s | %20s | %20s", student.getStudentId(), student.getName(), student.getGender().toString()));
		}
		return pendingStudentsList;
	}

	/**
	 * Method to approve a Student using Student's ID
	 */
	private void approveStudent() {
		
		List<Student> studentList = viewPendingAdmissions();
		if(studentList.size() == 0) {
			
			
			return;
		}
		
		System.out.println("Enter Student's ID:");
		String studentUserIdApproval = in.next();
		
		
		try {
			adminService.approveStudent(studentUserIdApproval, studentList);
			System.out.println("\nStudent Id : " +studentUserIdApproval+ " has been approved\n");
			//send notification from system
			notificationInterface.sendNotification(NotificationTypeConstant.REGISTRATION, studentUserIdApproval, null,0);
	
		} catch (StudentNotFoundForApprovalException e) {
			System.out.println(e.getMessage());
		}
	
		
	}

	/**
	 * Method to delete Course from catalogue
	 * @throws CourseNotFoundException 
	 */
	private void removeCourse() {
		
		System.out.println("Enter Course Code:");
		String courseId = in.next();
		
		try {
			adminService.removeCourse(courseId);
			System.out.println("\nCourse Deleted : "+courseId+"\n");
		} catch (CourseNotFoundException e) {
			
			System.out.println(e.getMessage());
		}
		catch (CourseNotDeletedException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method to add Course to catalogue
	 * @throws CourseExistsAlreadyException 
	 */
	private void addCourseToCatalogue() {

		in.nextLine();
		System.out.println("Course Id:");
		String courseId = in.nextLine();
		
		System.out.println("Enter Course Name:");
		String courseName = in.nextLine();
		
		Course course = new Course(courseId, courseName,"", 10);
		course.setCourseId(courseId);
		course.setCourseName(courseName);
		course.setSeats(10);
		
		try {
		adminService.addCourse(course);		
		}
		catch (CourseExistsAlreadyException e) {
			System.out.println("Course already existed "+e.getMessage());
		}

	}
	
	/**
	 * Method to display courses in catalogue
	 * @return List of courses in catalogue
	 */
	private List<Course> viewCoursesInCatalogue() {
		List<Course> courseList = adminService.viewCourses();
		if(courseList.size() == 0) {
			System.out.println("Nothing present in the catalogue!");
			return courseList;
		}
		System.out.println(String.format("%20s | %20s | %20s","COURSE CODE", "COURSE NAME", "INSTRUCTOR"));
		for(Course course : courseList) {
			System.out.println(String.format("%20s | %20s | %20s", course.getCourseId(), course.getCourseName(), course.getInstructorId()));
		}
		return courseList;
	}

}
