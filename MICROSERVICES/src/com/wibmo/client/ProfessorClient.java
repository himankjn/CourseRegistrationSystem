/**
 * 
 */
package com.wibmo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;
import com.wibmo.business.ProfessorInterface;
import com.wibmo.business.ProfessorService;


/**
 * @author himank
 *
 */
public class ProfessorClient {
	
	ProfessorInterface professorInterface = ProfessorService.getInstance();

	/**
	 * @param profID
	 */
	public void createMenu(String profID) {
		Scanner in = new Scanner(System.in);
		
		int input;
		while (true) {
			System.out.println("================================");
							   
			System.out.println("========Professor Menu========");
			System.out.println("================================");
			System.out.println("1. View Courses");
			System.out.println("2. View Enrolled Students");
			System.out.println("3. Add Grades");
			System.out.println("4. Exit");
			System.out.println("================================");
			System.out.printf("Choose From Menu: ");
			
			input = in.nextInt();
			switch (input) {
			case 1:
				System.out.println("PROF ID: "+profID);
				getCourses(profID);
				break;
			case 2:
				getCourses(profID);
				System.out.println("Enter the courseID for veiwing enrolled Students: ");
				String courseId= in.next();
				viewEnrolledStudents(courseId);
				break;
			case 3:
				addGrade(profID);
				break;
			case 4:
				pin.close();
				System.exit(0);
				return;
			default:
				System.out.println("Please select appropriate option...");
			}
		}
	}
	
	public void viewEnrolledStudents(String courseId) {
		System.out.println(String.format("%20s %20s %20s","Student Id","Student Name","Student Address" ));
		try {
			List<Student> enrolledStudents = new ArrayList<Student>();
			enrolledStudents = professorInterface.viewEnrolledStudents(courseId);
			for (Student obj: enrolledStudents) {
				System.out.println(String.format("%20s %20s %20s",obj.getStudentId(), obj.getName(),obj.getAddress()));
			}
			
		} catch(Exception ex) {
			System.out.println(ex.getMessage()+"Something went wrong, please try again later!");
		}
	}
	
	public void getCourses(String profId) {
		try {
			List<Course> coursesEnrolled = professorInterface.viewCourses(profId);
			System.out.println(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","CREDITS" ));
			for(Course obj: coursesEnrolled) {
				System.out.println(String.format("%20s %20s %20d",obj.getCourseId(), obj.getCourseName(), obj.getCredits()));
			}		
		} catch(Exception ex) {
			System.out.println("Something went wrong!"+ex.getMessage());
		}
	}
	
	public void addGrade(String profId) {	
		Scanner in = new Scanner(System.in);
		String courseId, grade, studentId;
		try {
			System.out.println("==============Add Grade==============");
			getCourses(profId);	
			System.out.printf("Enter course code: ");
			courseId = in.nextLine();
			viewEnrolledStudents(courseId);
			System.out.printf("Enter student id: ");
			studentId = in.nextLine();
			System.out.println("Enter grade: ");
			grade = in.nextLine();
			professorInterface.submitGrade(studentId, courseId, grade);
			System.out.println("GradeConstant added successfully for "+studentId);
		} catch(Exception ex) {
			System.out.println("GradeConstant cannot be added for"+ex.getStackTrace());
			
		} 
	}

}
