/**
 * 
 */
package com.wibmo.business;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.exception.CourseExistsAlreadyException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;

/**
 * 
 */
public class AdminServiceTest {

	private static AdminServiceInterface adminService=null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		adminService= AdminServiceImpl.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testViewCourses() {
		List<Course>courses =adminService.viewCourses();
		List<String>actualCourseNames=new ArrayList<String>();
		 for(Course course:courses) {
			 actualCourseNames.add(course.getCourseName());
		 }
		 List<String>expectedCourseNames= Arrays.asList("CPlusCplus",
				 "Cryptography",
				 "Algorithms",
				 "Deep Learning Basics",
				 "Java",
				 "Machine Learning Basics",
				 "Networking",
				 "Python",
				 "Software Architecture",
				 "Software Production Engineering");
		 
		 assertEquals(expectedCourseNames,actualCourseNames);
	}
		 
	@Test
	public void testAddDropCourse() throws CourseExistsAlreadyException, CourseNotFoundException, CourseNotDeletedException {
		System.out.println("Testing add Course!");
		adminService.addCourse(new Course("RF","RandomForest","bob123@gmail.com",10));
		List<Course> courses=adminService.viewCourses();
		List<String>actualCourseNames=new ArrayList<String>();
		 for(Course course:courses) {
			 actualCourseNames.add(course.getCourseName());
		 }
		 List<String>expectedCourseNames= Arrays.asList("CPlusCplus",
				 "Cryptography",
				 "Algorithms",
				 "Deep Learning Basics",
				 "Java",
				 "Machine Learning Basics",
				 "Networking",
				 "Python",
				 "RandomForest",
				 "Software Architecture",
				 "Software Production Engineering");
		 
		 assertEquals(expectedCourseNames,actualCourseNames);
		 
		 System.out.println("Testing Remove Course!");
		 adminService.removeCourse("RF");
		 courses=adminService.viewCourses();
		 actualCourseNames=new ArrayList<String>();
		 for(Course course:courses) {
			 actualCourseNames.add(course.getCourseName());
		 }
		 expectedCourseNames= Arrays.asList("CPlusCplus",
				 "Cryptography",
				 "Algorithms",
				 "Deep Learning Basics",
				 "Java",
				 "Machine Learning Basics",
				 "Networking",
				 "Python",
				 "Software Architecture",
				 "Software Production Engineering");
		 
		 assertEquals(expectedCourseNames,actualCourseNames);
		 
	}
	
	
	@Test
	public void testViewProfessor() {
		System.out.println("Testing View Professors!");
		List<Professor> professors=adminService.viewProfessors();
		List<String>actualProfessorNames=new ArrayList<String>();
		 for(Professor professor:professors) {
			 actualProfessorNames.add(professor.getName());
		 }
		 List<String>expectedProfessorNames= Arrays.asList("bob",
				 "george",
				 "R. Chandrashekhar");
		 
		 assertEquals(expectedProfessorNames,actualProfessorNames);
	}
	
	@Test
	public void testAddDropProfessor() throws Exception {
		System.out.println("Testing add/drop professor!");
		adminService.addProfessor(new Professor("MN", "Manish", GenderConstant.MALE, RoleConstant.PROFESSOR,"123", ""));
		List<Professor> professors=adminService.viewProfessors();
		List<String>actualProfessorNames=new ArrayList<String>();
		 for(Professor professor:professors) {
			 actualProfessorNames.add(professor.getName());
			 System.out.println(professor.getName());
		 }
		 List<String>expectedProfessorNames= Arrays.asList("bob",
				 "george",
				 "Manish",
				 "R. Chandrashekhar");
		
		 
	    adminService.dropProfessor("MN");
		professors=adminService.viewProfessors();
		actualProfessorNames=new ArrayList<String>();
		 for(Professor professor:professors) {
			 actualProfessorNames.add(professor.getName());
			 System.out.println(professor.getName());
		 }
		 expectedProfessorNames= Arrays.asList("bob",
				 "george",
				 "R. Chandrashekhar");
		 
		 
		 assertEquals(expectedProfessorNames,actualProfessorNames);
	

	
	}
	
	@Test
	public void testAssignCourses() throws Exception {
		System.out.println("Testing assign courses!");
		adminService.assignCourse("CR","bob123@gmail.com");
		String actual=null;
		String expected="bob123@gmail.com";
		List<Course> courses=adminService.viewCourses();
		List<String>actualCourseNames=new ArrayList<String>();
		 for(Course course:courses) {
			 if(course.getCourseId().equals("CR")) {
				 actual=course.getInstructorId();
			 }
		 }
		 
		assertEquals(expected,actual);
	}

	
	@Test
	public void generateReportCard() {
		List<RegisteredCourse> registeredCourses= adminService.generateGradeCard("parth123@gmail.com");
		List<String>actual=new ArrayList<String>();
		List<String>expected=Arrays.asList("Algorithms",
				"Networking",
				"Software Architecture",
				"Software Production Engineering",
				"Java", 
				"CPlusCplus");
		for(RegisteredCourse course: registeredCourses) {
			actual.add(course.getCourse().getCourseName());
		}
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testViewPendingAdmissions() {
		 List<Student> students=adminService.viewPendingAdmissions();
		 String expected= "Test Student";
		 String actual = students.get(0).getName();
		 assertEquals(expected,actual);
	}
	
}
