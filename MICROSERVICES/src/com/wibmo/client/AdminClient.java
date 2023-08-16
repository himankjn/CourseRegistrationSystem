/**
 * 
 */
package com.wibmo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;
import com.wibmo.business.AdminInterface;
import com.wibmo.business.AdminService;


/**
 * @author Shanmukh
 *
 */
public class AdminClient {

//	AdminInterface adminInterface = AdminService.getInstance();
//	
//	public void createMenu() {
//		Scanner in = new Scanner(System.in);
//		
//		int input;
//		while (true) {
//			System.out.println("--------------------------------");
//			System.out.println("-------Admin Menu-----------");
//			System.out.println("--------------------------------");
//			System.out.println("1. View Courses");
//			System.out.println("2. View Professors");
//			System.out.println("3. View Students");
//			System.out.println("4. Generate GradeCard");
//			System.out.println("5. Add Professor");
//			System.out.println("6. Remove Course");
//			System.out.println("7. Add Course");
//			System.out.println("8. Assign Course to a Professor");
//			System.out.println("--------------------------------");
//			System.out.printf("Choose From Menu: ");
//			
//			input = in.nextInt();
//			switch (input) {
//			case 1:
//				System.out.println("PROF ID: "+profID);
//				getCourses(profID);
//				break;
//			case 2:
//				System.out.println("Enter the courseID for veiwing enrolled Students: ");
//				String courseId= in.next();
//				viewEnrolledStudents(courseId);
//				break;
//			case 3:
//				addGrade(profID);
//				break;
//			case 4:
//				in.close();
//				System.exit(0);
//				return;
//			default:
//				System.out.println("Please select appropriate option...");
//			}
//		}
//	}
//	
//	public void viewEnrolledStudents(String courseId) {
//		System.out.println(String.format("%20s %20s %20s","Student Id","Student Name","Student Address" ));
//		try {
//			List<Student> enrolledStudents = new ArrayList<Student>();
//			enrolledStudents = professorInterface.viewEnrolledStudents(courseId);
//			for (Student obj: enrolledStudents) {
//				System.out.println(String.format("%20s %20s %20s",obj.getStudentId(), obj.getName(),obj.getAddress()));
//			}
//			
//		} catch(Exception ex) {
//			System.out.println(ex.getMessage()+"Something went wrong, please try again later!");
//		}
//	}
//	
//	public void getCourses(String profId) {
//		try {
//			List<Course> coursesEnrolled = professorInterface.viewCourses(profId);
//			System.out.println(String.format("%20s %20s %20s","COURSE CODE","COURSE NAME","CREDITS" ));
//			for(Course obj: coursesEnrolled) {
//				System.out.println(String.format("%20s %20s %20d",obj.getCourseId(), obj.getCourseName(), obj.getCredits()));
//			}		
//		} catch(Exception ex) {
//			System.out.println("Something went wrong!"+ex.getMessage());
//		}
//	}
//	
//	public void addGrade(String profId) {	
//		
//	}

}
