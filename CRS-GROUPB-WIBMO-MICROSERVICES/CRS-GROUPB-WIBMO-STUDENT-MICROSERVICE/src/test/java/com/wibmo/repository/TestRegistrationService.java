package com.wibmo.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.entity.Course;
import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseAlreadyRegisteredException;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotApplicableForSemesterException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.service.Impl.RegistrationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestRegistrationService {

	@InjectMocks
	private RegistrationServiceImpl registrationService;
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Mock
	StudentRepository studentRepository;
	

	@Test
	public void testAddCourse() throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException, CourseAlreadyRegisteredException, CourseNotApplicableForSemesterException {
		String studentId = "Avanthi22@yahoo.com";
		String courseId = "JV";
		
		Optional<Student> student = Optional.ofNullable(new Student());
		Optional<Course> course = Optional.ofNullable(new Course());
		
		student.get().setStudentId(studentId);
		student.get().setSem(2);
		
		course.get().setCourseId(courseId);
		course.get().setSem(2);
		
		List<Object[]>availableCoursesFetched = new ArrayList<Object[]>();
		Object[] course1 = {"HCV",9,"Heavy Computer Vision","AB",200.0,2};
		Object[] course2 = {"JV",9,"Heavy Integrated Vision","AB",1000.0,2};
		
	
		
		availableCoursesFetched.add(course1);
		availableCoursesFetched.add(course2);
		
		when(registeredCourseRepository.availableCoursesByStudentIdAndSem(studentId,2)).thenReturn(availableCoursesFetched);
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		when(studentRepository.findById(studentId)).thenReturn(student);
		when(courseRepository.findByCourseId(courseId)).thenReturn(course);
		when(registeredCourseRepository.numberOfRegisteredCourses(studentId)).thenReturn(3);
		when(registeredCourseRepository.existsByCourseIdAndStudentId(courseId, studentId)).thenReturn(false);
		when(courseRepository.existsSeatsByCourseId(courseId)).thenReturn(true);
		doNothing().when(courseRepository).decrementSeats(Mockito.any());
		when(registeredCourseRepository.save(Mockito.any())).thenReturn(new RegisteredCourse());
		
		boolean succ = registrationService.addCourse(courseId,studentId);
		verify(registeredCourseRepository,times(1)).save(Mockito.any());
		assertEquals(succ,true);
	}
	
	@Test
	public void testAddCourseCourseLimitExceededException() throws CourseLimitExceededException,SQLException,CourseNotApplicableForSemesterException {
		String studentId = "Avanthi22@yahoo.com";
		String courseId = "JV";
		
		Optional<Student> student = Optional.ofNullable(new Student());
		Optional<Course> course = Optional.ofNullable(new Course());
		
		student.get().setStudentId(studentId);
		student.get().setSem(2);
		
		course.get().setCourseId(courseId);
		course.get().setSem(2);
		
		List<Object[]>availableCoursesFetched = new ArrayList<Object[]>();
		Object[] course1 = {"HCV",9,"Heavy Computer Vision","AB",200.0,2};
		Object[] course2 = {"HIV",9,"Heavy Integrated Vision","AB",1000.0,2};
		
	
		
		availableCoursesFetched.add(course1);
		availableCoursesFetched.add(course2);
		
		when(registeredCourseRepository.availableCoursesByStudentIdAndSem(studentId,2)).thenReturn(availableCoursesFetched);
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		when(studentRepository.findById(studentId)).thenReturn(student);
		when(courseRepository.findByCourseId(courseId)).thenReturn(course);
		when(registeredCourseRepository.numberOfRegisteredCourses(studentId)).thenReturn(6);
		
		assertThrows(CourseLimitExceededException.class,()->registrationService.addCourse(courseId,studentId));
	}
	
	@Test
	public void testAddCourseAlreadyRegisteredException() throws CourseLimitExceededException,SQLException, CourseAlreadyRegisteredException, CourseNotApplicableForSemesterException {
		String studentId = "Avanthi22@yahoo.com";
		String courseId = "JV";
		
		Optional<Student> student = Optional.ofNullable(new Student());
		Optional<Course> course = Optional.ofNullable(new Course());
		
		student.get().setStudentId(studentId);
		student.get().setSem(2);
		
		course.get().setCourseId(courseId);
		course.get().setSem(2);
		
		List<Object[]>availableCoursesFetched = new ArrayList<Object[]>();
		Object[] course1 = {"HCV",9,"Heavy Computer Vision","AB",200.0,2};
		Object[] course2 = {"HIV",9,"Heavy Integrated Vision","AB",1000.0,2};
		
	
		
		availableCoursesFetched.add(course1);
		availableCoursesFetched.add(course2);
		
		when(registeredCourseRepository.availableCoursesByStudentIdAndSem(studentId,2)).thenReturn(availableCoursesFetched);
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		when(studentRepository.findById(studentId)).thenReturn(student);
		when(courseRepository.findByCourseId(courseId)).thenReturn(course);
		when(registeredCourseRepository.numberOfRegisteredCourses(studentId)).thenReturn(3);
		when(registeredCourseRepository.existsByCourseIdAndStudentId(courseId, studentId)).thenReturn(true);
		
		assertThrows(CourseAlreadyRegisteredException.class,()->registrationService.addCourse(courseId,studentId));
	}
	
	@Test
	public void testAddCourseCourseNotFoundException() throws CourseNotFoundException,CourseLimitExceededException, SQLException, CourseAlreadyRegisteredException, CourseNotApplicableForSemesterException {
		String studentId = "Avanthi22@yahoo.com";
		String courseId = "JV";
		
		Optional<Student> student = Optional.ofNullable(new Student());
		Optional<Course> course = Optional.ofNullable(new Course());
		
		student.get().setStudentId(studentId);
		student.get().setSem(2);
		
		course.get().setCourseId(courseId);
		course.get().setSem(2);
		
		List<Object[]>availableCoursesFetched = new ArrayList<Object[]>();
		Object[] course1 = {"HCV",9,"Heavy Computer Vision","AB",200.0,2};
		Object[] course2 = {"HIV",9,"Heavy Integrated Vision","AB",1000.0,2};
		
	
		
		availableCoursesFetched.add(course1);
		availableCoursesFetched.add(course2);
		
		when(registeredCourseRepository.availableCoursesByStudentIdAndSem(studentId,2)).thenReturn(availableCoursesFetched);
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		when(studentRepository.findById(studentId)).thenReturn(student);
		when(courseRepository.findByCourseId(courseId)).thenReturn(course);
		when(registeredCourseRepository.numberOfRegisteredCourses(studentId)).thenReturn(3);
		when(registeredCourseRepository.existsByCourseIdAndStudentId(courseId, studentId)).thenReturn(false);
		
		assertThrows(CourseNotFoundException.class,()->registrationService.addCourse(courseId,studentId));
	}
	
	@Test
	public void testAddCourseSeatNotAvailableException() throws CourseNotFoundException,CourseLimitExceededException,SeatNotAvailableException, SQLException, CourseAlreadyRegisteredException, CourseNotApplicableForSemesterException {
		String studentId = "Avanthi22@yahoo.com";
		String courseId = "JV";
		
		Optional<Student> student = Optional.ofNullable(new Student());
		Optional<Course> course = Optional.ofNullable(new Course());
		
		student.get().setStudentId(studentId);
		student.get().setSem(2);
		
		course.get().setCourseId(courseId);
		course.get().setSem(2);
		
		List<Object[]>availableCoursesFetched = new ArrayList<Object[]>();
		Object[] course1 = {"HCV",9,"Heavy Computer Vision","AB",200.0,2};
		Object[] course2 = {"JV",9,"Heavy Integrated Vision","AB",1000.0,2};
		
	
		
		availableCoursesFetched.add(course1);
		availableCoursesFetched.add(course2);
		
		when(registeredCourseRepository.availableCoursesByStudentIdAndSem(studentId,2)).thenReturn(availableCoursesFetched);
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		when(studentRepository.findById(studentId)).thenReturn(student);
		when(courseRepository.findByCourseId(courseId)).thenReturn(course);
		when(registeredCourseRepository.numberOfRegisteredCourses(studentId)).thenReturn(3);
		when(registeredCourseRepository.existsByCourseIdAndStudentId(courseId, studentId)).thenReturn(false);
		when(courseRepository.existsSeatsByCourseId(courseId)).thenReturn(false);
		
		assertThrows(SeatNotAvailableException.class,()->registrationService.addCourse(courseId,studentId));
	}
	
	@Test
	public void testDropCourse() throws CourseNotFoundException, SQLException {
		String CourseId = "JV";
		String studentId = "HCV";
		
		List<Course>registeredCourseList = new ArrayList<Course>();
		Course course1 = new Course();
		course1.setCourseId("JV");
		
		Course course2 = new Course();
		course2.setCourseId("ML");
		
		registeredCourseList.add(course1);
		registeredCourseList.add(course2);
		
		doNothing().when(courseRepository).incrementSeats(Mockito.any());
		doNothing().when(registeredCourseRepository).deleteById(Mockito.any());
		
		boolean succ = registrationService.dropCourse(CourseId, studentId, registeredCourseList);
		verify(registeredCourseRepository,times(1)).deleteById(Mockito.any());
		verify(courseRepository,times(1)).incrementSeats(Mockito.any());
		assertEquals(succ,true);
		
	}

	@Test
	public void testDropCourseCourseNotFoundException() throws CourseNotFoundException, SQLException {
		String CourseId = "JV";
		String studentId = "HCV";
		
		List<Course>registeredCourseList = new ArrayList<Course>();
		Course course1 = new Course();
		course1.setCourseId("JAV");
		
		Course course2 = new Course();
		course2.setCourseId("ML");
		
		registeredCourseList.add(course1);
		registeredCourseList.add(course2);
		
		assertThrows(CourseNotFoundException.class,()->registrationService.dropCourse(CourseId, studentId, registeredCourseList));
		
	}

	@Test public void testCalculateFee() throws SQLException {
		String studentId = "Avanthi22@yahoo.com";
		int sem = 2;
		
		Optional<Student> student = Optional.ofNullable(new Student());
		student.get().setStudentId(studentId);
		student.get().setSem(sem);
		
		when(studentRepository.findById(studentId)).thenReturn(student);
		when(courseRepository.calculateFeeForStudentWithSem(studentId,sem)).thenReturn(1000.0);
		
		double feeExpected = 1000.0;
		double feeCalculated = registrationService.calculateFee(studentId);
		assertEquals(feeExpected,feeCalculated,0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test public void testAvailableCourses() throws SQLException{
		String studentId = "Avanthi22@yahoo.com";
		int sem = 2;
		
		Optional<Student> student = Optional.ofNullable(new Student());
		student.get().setStudentId(studentId);
		student.get().setSem(sem);
		
		
		List<Object[]>availableCourseObjectsFetched = new ArrayList<Object[]>();
		Object[] course1 = {"HCV",9,"Heavy Computer Vision","AB",200.0,2};
		Object[] course2 = {"JV",9,"Heavy Integrated Vision","AB",1000.0,2};
		
		List<Course>AvailableCoursesExpected = new ArrayList<Course>();
		Course available1 = new Course();
		available1.setCourseId("HCV");
		available1.setSeats(9);
		available1.setCourseName("Heavy Computer Vision");
		available1.setInstructorId("AB");
		available1.setCourseFee(200.0);
		available1.setSem(sem);
		
		Course available2 = new Course();
		available2.setCourseId("JV");
		available2.setSeats(9);
		available2.setCourseName("Heavy Integrated Vision");
		available2.setInstructorId("AB");
		available2.setCourseFee(1000.0);
		available2.setSem(sem);
		
		availableCourseObjectsFetched.add(course1);
		availableCourseObjectsFetched.add(course2);
		
		AvailableCoursesExpected.add(available1);
		AvailableCoursesExpected.add(available2);
		
		when(registeredCourseRepository.availableCoursesByStudentIdAndSem(studentId,sem)).thenReturn(availableCourseObjectsFetched);
		when(studentRepository.findById(studentId)).thenReturn(student);
		
		List<Course>AvailableCoursesActual = registrationService.viewAvailableCourses(studentId);
		
		Assertions.assertThat(AvailableCoursesActual)
        .usingFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(AvailableCoursesExpected);
	}
	
	
	@SuppressWarnings("deprecation")
	@Test public void testRegisteredCourses() throws SQLException{
		String studentId = "Avanthi22@yahoo.com";
		int sem = 2;
		
		Optional<Student> student = Optional.ofNullable(new Student());
		student.get().setStudentId(studentId);
		student.get().setSem(sem);
		
		
		List<Object[]>RegisteredCourseObjectsFetched = new ArrayList<Object[]>();
		Object[] course1 = {"HCV",9,"Heavy Computer Vision","AB",200.0,2};
		Object[] course2 = {"JV",9,"Heavy Integrated Vision","AB",1000.0,2};
		
		List<Course>RegisteredCoursesExpected = new ArrayList<Course>();
		Course registered1 = new Course();
		registered1.setCourseId("HCV");
		registered1.setSeats(9);
		registered1.setCourseName("Heavy Computer Vision");
		registered1.setInstructorId("AB");
		registered1.setCourseFee(200.0);
		registered1.setSem(sem);
		
		Course registered2 = new Course();
		registered2.setCourseId("JV");
		registered2.setSeats(9);
		registered2.setCourseName("Heavy Integrated Vision");
		registered2.setInstructorId("AB");
		registered2.setCourseFee(1000.0);
		registered2.setSem(sem);
		
		RegisteredCourseObjectsFetched.add(course1);
		RegisteredCourseObjectsFetched.add(course2);
		
		RegisteredCoursesExpected.add(registered1);
		RegisteredCoursesExpected.add(registered2);
		
		when(registeredCourseRepository.enrolledCoursesByStudentIdAndSem(studentId,sem)).thenReturn(RegisteredCourseObjectsFetched);
		when(studentRepository.findById(studentId)).thenReturn(student);
		
		List<Course>RegisteredCoursesActual = registrationService.viewRegisteredCourses(studentId);
		
		Assertions.assertThat(RegisteredCoursesActual)
        .usingFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(RegisteredCoursesExpected);
	}
	
	@Test void testGetRegistrationStatus() throws SQLException {
		String studentId = "Avanth22@yahoo.com";
		Optional<Student> student = Optional.ofNullable(new Student());
		
		student.get().setStudentId(studentId);
		student.get().setRegistered(false);
		
		when(studentRepository.findById(studentId)).thenReturn(student);
		
		boolean expectedRegistrationStatus = false;
		boolean actualRegistrationStatus = registrationService.getRegistrationStatus(studentId);
		
		assertEquals(expectedRegistrationStatus,actualRegistrationStatus);
	}
	
	@Test void testSetRegistrationStatus() throws SQLException {
		String studentId = "Avanth22@yahoo.com";
		Optional<Student> student = Optional.ofNullable(new Student());
		
		student.get().setStudentId(studentId);
		
		when(studentRepository.findById(studentId)).thenReturn(student);
		registrationService.setRegistrationStatus(studentId);
		verify(studentRepository,times(1)).findById(studentId);
		
	}
	
	@Test void testGetPaymentStatus() throws SQLException {
		String studentId = "Avanth22@yahoo.com";
		Optional<Student> student = Optional.ofNullable(new Student());
		
		student.get().setStudentId(studentId);
		student.get().setPaid(false);
		
		when(studentRepository.findById(studentId)).thenReturn(student);
		
		boolean expectedPaymentStatus = false;
		boolean actualPaymentStatus = registrationService.getPaymentStatus(studentId);
		
		assertEquals(expectedPaymentStatus,actualPaymentStatus);
	}
	
	@Test void testSetPaymentStatus() throws SQLException {
		String studentId = "Avanth22@yahoo.com";
		Optional<Student> student = Optional.ofNullable(new Student());
		
		student.get().setStudentId(studentId);
		
		when(studentRepository.findById(studentId)).thenReturn(student);
		registrationService.setPaymentStatus(studentId);
		verify(studentRepository,times(1)).findById(studentId);
		
	}
	
	@Test void testSetSemForStudent() {
		String studentId = "Avanth22@yahoo.com";
		int sem = 3;
		Optional<Student> student = Optional.ofNullable(new Student());
		
		student.get().setStudentId(studentId);
		doNothing().when(studentRepository).setSem(studentId, sem);
		registrationService.setSemForStudent(studentId, sem);
		verify(studentRepository,times(1)).setSem(studentId, sem);
	}
}
