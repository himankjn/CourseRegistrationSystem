import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wibmo.bean.Course;
import com.wibmo.bean.GradeCard;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.business.RegistrationServiceImpl;
import com.wibmo.business.RegistrationServiceInterface;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;

public class RegistrationTest {

	private static RegistrationServiceInterface registrationService=null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		registrationService = RegistrationServiceImpl.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testViewAddDropAvailableCourse() throws SQLException, CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException {
		System.out.println("Testing ViewRegistered Courses for a Student!");
		
		String studentId = "himank123@gmail.com";
		String courseId2bAdded = "JV";
		List<String>AllCourses = Arrays.asList("CPP","CR","CS10","DL101","JV","ML101","NW","PY","SA01","SPE");
		List<String>ExpectedRegisteredCourseIds = new ArrayList<String>();
		List<String>HimankCoures = Arrays.asList("SA01","SPE");
		List<Course>RegisteredCourses = registrationService.viewRegisteredCourses(studentId);
		
		for(Course c: RegisteredCourses) {
			ExpectedRegisteredCourseIds.add(c.getCourseId());
		}
		
		Collections.sort(AllCourses);
		Collections.sort(ExpectedRegisteredCourseIds);
		
		assertEquals(HimankCoures,ExpectedRegisteredCourseIds);
		
		System.out.println("Testing ViewAvailable Courses for a Student!");
		
		List<String>ExpectedAvailableCourses = new ArrayList<String>(AllCourses);
		ExpectedAvailableCourses.removeAll(HimankCoures);
		List<String>ActualAvailableCourses = new ArrayList<String>(AllCourses);
		ActualAvailableCourses.removeAll(ExpectedRegisteredCourseIds);
		
		assertEquals(ExpectedAvailableCourses,ActualAvailableCourses);
		
		System.out.println("Testing add Course for a student Registration!");
		registrationService.addCourse(courseId2bAdded, studentId);	
		ExpectedRegisteredCourseIds.add(courseId2bAdded);
		List<String>ActualRegisteredCourseIds = new ArrayList<String>();
		for(Course c: registrationService.viewRegisteredCourses(studentId)) {
			ActualRegisteredCourseIds.add(c.getCourseId());
		}
		
		Collections.sort(ExpectedRegisteredCourseIds);
		Collections.sort(ActualRegisteredCourseIds);
		
		assertEquals(ExpectedRegisteredCourseIds,ActualRegisteredCourseIds);
		
		System.out.println("Testing Drop Course for a student Registration!");
		ExpectedRegisteredCourseIds.remove("JV");
		RegisteredCourses = registrationService.viewRegisteredCourses(studentId);
		registrationService.dropCourse(courseId2bAdded, studentId,RegisteredCourses);
		
		for(Course c: registrationService.viewRegisteredCourses(studentId)) {
			ActualRegisteredCourseIds.add(c.getCourseId());
		}
		
		Collections.sort(ExpectedRegisteredCourseIds);
		Collections.sort(ActualRegisteredCourseIds);
		assertEquals(ExpectedRegisteredCourseIds,ActualRegisteredCourseIds);
		
	}

	@Test
	public void testViewGradeCard() throws SQLException {
		String studentId = "Ram@Jai";
		
		GradeCard ramGradeSheet = registrationService.viewGradeCard(studentId);
		List<String>CourseGradesExpected = Arrays.asList("A","NOT_GRADED","NOT_GRADED","NOT_GRADED","NOT_GRADED","NOT_GRADED");
		List<String>CourseGradesActual = new ArrayList<String>();
		
		for(RegisteredCourse course: ramGradeSheet.getReg_list()) {
			CourseGradesActual.add(course.getGrade().toString());
		}
		
		assertEquals(CourseGradesExpected,CourseGradesActual);
	}


	@Test
	public void testGetSetRegistrationStatus() throws SQLException {
		String studentId = "himank123@gmail.com";
		
		System.out.println("Testing GetRegistrationStatus of a Student!");
		boolean originalStatus = registrationService.getRegistrationStatus(studentId);
		assertEquals(originalStatus,false);
		
		System.out.println("Testing SetRegistrationStatus of a Student!");
		registrationService.setRegistrationStatus(studentId);
		
		boolean currentStatus = registrationService.getRegistrationStatus(studentId);
		assertEquals(currentStatus, true);
	
	}


	@Test
	public void testIsReportGenerated() throws SQLException {
		String studentId = "Ram@Jai";
		boolean isReportGeneratedExpected = true;
		boolean isReportGeneratedActual = registrationService.isReportGenerated(studentId);
		
		assertEquals(isReportGeneratedActual,isReportGeneratedExpected);
	}

	@Test
	public void testGetSetPaymentStatus() throws SQLException {
		String studentId = "himank123@gmail.com";
		
		System.out.println("Testing GetRegistrationStatus of a Student!");
		boolean originalStatus = registrationService.getPaymentStatus(studentId);
		assertEquals(originalStatus,false);
		
		System.out.println("Testing SetRegistrationStatus of a Student!");
		registrationService.setPaymentStatus(studentId);
		
		boolean currentStatus = registrationService.getPaymentStatus(studentId);
		assertEquals(currentStatus, true);
	
	}

}
