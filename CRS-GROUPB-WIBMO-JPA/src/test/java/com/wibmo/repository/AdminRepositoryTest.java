package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.service.Impl.AdminServiceImpl;
import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;
import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseExistsAlreadyException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.ProfessorNotFoundException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyInUseException;
import com.wibmo.exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AdminRepositoryTest {
	
	@InjectMocks
	private AdminServiceImpl adminService;
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private ProfessorRepository professorRepository;
	
	@Mock
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Mock
	private ProfessorCourseRequestRepository professorCourseRequestRepository;
	
	@Mock
	private StudentRepository studentRepository;
	
	@Test
	public void testViewCourses() {
		List<Course> courseList= new ArrayList<Course>();
		Course c1 = new Course();
		Course c2= new Course();
		Course c3= new Course();
		courseList.add(c1);
		courseList.add(c2);
		courseList.add(c3);
		
		when(courseRepository.findAll()).thenReturn(courseList);
		List<Course> actual= adminService.viewCourses();
		assertEquals(3,actual.size());
		
	}
	
	@Test
	public void testViewProfessors() {
		List<Professor> professorList= new ArrayList<Professor>();
		Professor p1=new Professor();
		Professor p2=new Professor();
		Professor p3=new Professor();
		professorList.add(p1);
		professorList.add(p2);
		
		when(professorRepository.findAll()).thenReturn(professorList);
		List<Professor> actual= adminService.viewProfessors();
		assertEquals(2,actual.size());
	}
	
	@Test
	public void testViewPendingAdmissions() {
		List<Student> pendingStudentList= new ArrayList<Student>();
		pendingStudentList.add(new Student());
		when(studentRepository.findAllByIsApproved(false)).thenReturn(pendingStudentList);
		
		List<Student> actual= adminService.viewPendingAdmissions();
		assertEquals(1,actual.size());
	}
	
	
	@Test
	public void testAddCourse() throws CourseExistsAlreadyException {
		Course c1= new Course();
		c1.setCourseId("cId");
		List<Course> courseList=new ArrayList<Course>();
		when(courseRepository.findAll()).thenReturn(courseList);
		when(courseRepository.save(c1)).thenReturn(c1);
		adminService.addCourse(c1);
		verify(courseRepository,times(1)).save(c1);
	}
	
	@Test
	public void testAddCourseCourseExistsAlreadyException(){
		Course c1= new Course();
		c1.setCourseId("cId");
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(c1);
		when(courseRepository.findAll()).thenReturn(courseList);
		assertThrows(CourseExistsAlreadyException.class,()->{
			adminService.addCourse(c1);
			});
		
	}
	
	@Test
	public void testRemoveCourse() throws CourseNotFoundException, CourseNotDeletedException {
		Course c1= new Course();
		c1.setCourseId("cId");
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(c1);
		when(courseRepository.findAll()).thenReturn(courseList);
		doNothing().when(courseRepository).deleteById("cId");
		adminService.removeCourse("cId");
		verify(courseRepository,times(1)).deleteById("cId");
	}
	
	@Test
	public void testRemoveCourse_CourseNotFoundException() {
		Course c1= new Course();
		c1.setCourseId("cId");
		List<Course> courseList=new ArrayList<Course>();
		when(courseRepository.findAll()).thenReturn(courseList);
		assertThrows(CourseNotFoundException.class,()->{
			adminService.removeCourse("cId");
		});
	}
	
	@Test
	public void testApproveSingleStudent() throws StudentNotFoundForApprovalException {
		List<Student> pendingStudentList= new ArrayList<Student>();
		Student s1= new Student();
		s1.setStudentId("sId");
		pendingStudentList.add(s1);
		when(studentRepository.findAllByIsApproved(false)).thenReturn(pendingStudentList);
		doNothing().when(studentRepository).setIsApprovedById(true, "sId");
		adminService.approveSingleStudent("sId");
		verify(studentRepository,times(1)).setIsApprovedById(true,"sId");
		
	}
	
	
	@Test
	public void testApproveSingleStudent_StudentNotFoundForApprovalException() {
		List<Student> pendingStudentList= new ArrayList<Student>();
		Student s1= new Student();
		s1.setStudentId("sId");
		pendingStudentList.add(s1);
		when(studentRepository.findAllByIsApproved(false)).thenReturn(pendingStudentList);
		assertThrows(StudentNotFoundForApprovalException.class,()->{
			adminService.approveSingleStudent("s2Id");
		});
		
	}
	
	@Test
	public void testApproveAllStudents()  {
		doNothing().when(studentRepository).setAllIsApproved(true);
		adminService.approveAllStudents();
		verify(studentRepository,times(1)).setAllIsApproved(true);
	}
	
	
	@Test
	public void testAddProfessor() throws ProfessorNotAddedException, UserIdAlreadyInUseException {
		Professor p1=new Professor();
		p1.setProfessorId("pId");
		when(professorRepository.save(p1)).thenReturn(p1);
		adminService.addProfessor(p1);
		verify(professorRepository,times(1)).save(p1);
	}
	
	@Test
	public void testDropProfessor() throws ProfessorNotFoundException {
		Professor p1= new Professor();
		p1.setProfessorId("pId");
		List<Professor> professorList= new ArrayList<Professor>();
		professorList.add(p1);
		when(professorRepository.findAll()).thenReturn(professorList);
		doNothing().when(professorRepository).deleteById("pId");
		adminService.dropProfessor("pId");
		verify(professorRepository,times(1)).deleteById("pId");
		
		
	}
	
	@Test
	public void testDropProfessor_ProfessorNotFoundException() {
		Professor p1= new Professor();
		p1.setProfessorId("p2d");
		List<Professor> professorList= new ArrayList<Professor>();
		professorList.add(p1);
		when(professorRepository.findAll()).thenReturn(professorList);
		assertThrows(ProfessorNotFoundException.class, ()->{
			adminService.dropProfessor("pId");
		});
		
	}
	
	
	@Test
	public void testAssignCourse() throws CourseNotFoundException, UserNotFoundException {
		Professor p1= new Professor();
		p1.setProfessorId("pId");
		p1.setUserId("pId");
		List<Professor> professorList= new ArrayList<Professor>();
		professorList.add(p1);
		when(professorRepository.findAll()).thenReturn(professorList);
		
		Course c1= new Course();
		c1.setCourseId("cId");
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(c1);
		when(courseRepository.findAll()).thenReturn(courseList);
		
		adminService.assignCourse("cId","pId");
		verify(courseRepository,times(1)).assignCourse("cId", "pId");
	}
	
	@Test
	public void testAssignCourse_UserNotFoundException() {
		Professor p1= new Professor();
		p1.setProfessorId("p2d");
		p1.setUserId("p2d");
		List<Professor> professorList= new ArrayList<Professor>();
		professorList.add(p1);
		when(professorRepository.findAll()).thenReturn(professorList);
		
//		Course c1= new Course();
//		c1.setCourseId("cId");
//		List<Course> courseList=new ArrayList<Course>();
//		courseList.add(c1);
//		when(courseRepository.findAll()).thenReturn(courseList);
//		
		assertThrows(UserNotFoundException.class,()->{
			adminService.assignCourse("cId","pId");
		});
	}
	
	@Test
	public void testAssignCourse_CourseNotFoundException() {
		Professor p1= new Professor();
		p1.setProfessorId("pId");
		p1.setUserId("pId");
		List<Professor> professorList= new ArrayList<Professor>();
		professorList.add(p1);
		when(professorRepository.findAll()).thenReturn(professorList);
		
		Course c1= new Course();
		c1.setCourseId("c2d");
		List<Course> courseList=new ArrayList<Course>();
		courseList.add(c1);
		when(courseRepository.findAll()).thenReturn(courseList);
		
		assertThrows(CourseNotFoundException.class,()->{
			adminService.assignCourse("cId","pId");
		});
	}
	
	@Test
	public void testGenereateGradeCard() {
		Student s1= new Student();
		s1.setStudentId("sId");
		s1.setSem(3);
		when(studentRepository.findById("sId")).thenReturn(Optional.of(s1));
		List<RegisteredCourse> registeredCourses= new ArrayList<RegisteredCourse>();
		
		when(registeredCourseRepository.findByRegisteredCourseIdStudentIdAndSem("sId",3)).
		thenReturn(registeredCourses);
		doNothing().when(studentRepository).setGeneratedReportCardTrue("sId");
		
		assertEquals(registeredCourses,adminService.generateGradeCard("sId"));
		verify(studentRepository,times(1)).setGeneratedReportCardTrue("sId");
	}
	
	
	
}
