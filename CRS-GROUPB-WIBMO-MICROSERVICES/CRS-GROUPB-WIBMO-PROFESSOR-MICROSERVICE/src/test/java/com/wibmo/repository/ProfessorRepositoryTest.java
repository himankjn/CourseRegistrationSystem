
package com.wibmo.repository;



import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.GradeConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.entity.Course;
import com.wibmo.entity.EnrolledStudent;
import com.wibmo.entity.Professor;
import com.wibmo.entity.ProfessorCourseRequest;
import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseExistsAlreadyException;
import com.wibmo.exception.InvalidCourseAssignmentRequestException;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.service.Impl.ProfessorServiceImpl;
import com.wibmo.validator.AdminValidator;
import com.wibmo.validator.ProfessorValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



import java.util.ArrayList;
import java.util.List;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
class ProfessorRepositoryTest {

	@InjectMocks
	ProfessorServiceImpl professorService;



	@Mock
	ProfessorRepository professorRepository;

	@Mock
	CourseRepository courseRepository;


	@Mock 
	RegisteredCourseRepository registeredCourseRepository;

	@Mock 
	ProfessorCourseRequestRepository professorCourseRequestRepository;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}



	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}


	@Test
	void TestViewAssignedCourses() {
		List<Course> coursesOffered = new ArrayList<Course>();  
		Course course1=new Course();
		Course course2=new Course();
		course1.setCourseFee(200);
		course1.setCourseId("AN");
		course1.setCourseName("aman");
		course1.setInstructorId("bob");
		course1.setSeats(20);
		course2.setCourseFee(100);
		course2.setCourseId("SE");
		course2.setCourseName("chaman");
		course2.setInstructorId("mark");
		course2.setSeats(30);
		coursesOffered.add(course1);
		coursesOffered.add(course2);
		String profId="bob";
		when(courseRepository.findByInstructorId(profId)).thenReturn(coursesOffered);
		List<Course> actualCourses=professorService.viewAssignedCourses(profId);
		assertEquals(2, actualCourses.size());
	}

	@Test
	void TestGetUnassignedCourses() {
		List<Course> coursesUnAssigned=new ArrayList<Course>();
		Course course1=new Course();
		Course course2=new Course();
		course1.setCourseFee(200);
		course1.setCourseId("AN");
		course1.setCourseName("aman");
		course1.setInstructorId("bob");
		course1.setSeats(20);
		course2.setCourseFee(100);
		course2.setCourseId("SE");
		course2.setCourseName("chaman");
		course2.setInstructorId("mark");
		course2.setSeats(30);
		coursesUnAssigned.add(course1);
		coursesUnAssigned.add(course2);
		when(courseRepository.findByInstructorId("NOT_ASSIGNED")).thenReturn(coursesUnAssigned);
		List<Course> actualCoursesUnAssigned=professorService.getUnassignedCourses();
		assertEquals(2, actualCoursesUnAssigned.size());	
	}

	@Test
	void TestSubmitGrade() {
		String studentId="fddf";
		String courseId="sds";
		String grade="B";
		doNothing().when(registeredCourseRepository).addGrade(studentId,courseId,GradeConstant.valueOf(grade));
		professorService.submitGrade( studentId, courseId, grade);
		verify(registeredCourseRepository,times(1)).addGrade(studentId,courseId,GradeConstant.valueOf(grade));

	}

	@Test void TestViewEnrolledStudents() {
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		EnrolledStudent enrolledStudent1=new EnrolledStudent();
		EnrolledStudent enrolledStudent2=new EnrolledStudent();
		enrolledStudent1.setStudentId("wad");
		enrolledStudent2.setStudentId("SD");
		enrolledStudents.add(enrolledStudent2);
		enrolledStudents.add(enrolledStudent1);
		String courseId="sed";

		when(registeredCourseRepository.getEnrolledStudents(courseId)).thenReturn(enrolledStudents);
		List<EnrolledStudent> actualEnrolledStudents=professorService.viewEnrolledStudents(courseId);
		assertEquals(enrolledStudents,enrolledStudents);
	}
		


	@Test
	void TestRequestCourseAssignment() {
		ProfessorCourseRequest professorCourseRequest =new ProfessorCourseRequest();
		professorCourseRequest.setCourseId("dfd");
		professorCourseRequest.setuserId("asda");
		List<Course> unassignedCourses=new ArrayList<Course>();
		Course course=new Course();
		course.setCourseId("dfd");
		unassignedCourses.add(course);
		
		
		when(courseRepository.findByInstructorId("NOT_ASSIGNED")).thenReturn(unassignedCourses);

		when(professorCourseRequestRepository.save(Mockito.any())).thenReturn(professorCourseRequest);
		assertEquals(true, professorService.requestCourseAssignment("asda", "dfd"));
	}
}