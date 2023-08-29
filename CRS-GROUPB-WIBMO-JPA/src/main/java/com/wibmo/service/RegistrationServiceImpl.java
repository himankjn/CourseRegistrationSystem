package com.wibmo.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.GradeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.constants.SQLQueriesConstant;
import com.wibmo.entity.Course;
import com.wibmo.entity.Grade;
import com.wibmo.entity.GradeCard;
import com.wibmo.entity.Notification;
import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.RegisteredCourseId;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseLimitExceededException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.SeatNotAvailableException;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.ProfessorCourseRequestRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.RegisteredCourseRepository;
import com.wibmo.repository.RegistrationDAOImpl;
import com.wibmo.repository.RegistrationDAOInterface;
import com.wibmo.repository.StudentRepository;
import com.wibmo.utils.DBUtils;
import com.wibmo.validator.StudentValidator;

/**
 * @author bhuvan
 * The Registration Operation provides the business logic for student registration.
 * 
 */

@Service
public class RegistrationServiceImpl implements RegistrationServiceInterface {
	private static final Logger logger = LogManager.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	RegistrationDAOInterface registrationDAOInterface;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Autowired
	private ProfessorCourseRequestRepository professorCourseRequestRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	/**
	 * Method to add Course selected by student 
	 * @param courseCode
	 * @param studentId
	 * @param courseList 
	 * @return boolean indicating if the course is added successfully
	 * @throws CourseNotFoundException
	 * @throws SeatNotAvailableException 
	 * @throws CourseLimitExceedException 
	 * @throws SQLException 
	 */
	@Override
	
	public boolean addCourse(String courseCode, String studentId) throws CourseNotFoundException, CourseLimitExceededException, SeatNotAvailableException, SQLException 
	{
       
		
		List<Course> availableCourseList=viewCourses(studentId);
		if (registeredCourseRepository.numberOfRegisteredCourses(studentId) >= 6)
		{	
			logger.info("You have already registered for 6 courses");
		}
		else if (registeredCourseRepository.existsByCourseIdAndStudentId(courseCode, studentId)) 
		{
			return false;
		} 
		else if (!courseRepository.existsSeatsByCourseId(courseCode)) 
		{
			throw new SeatNotAvailableException(courseCode);
		} 
		else if(!StudentValidator.isValidCourseCode(courseCode, availableCourseList))
		{
			throw new CourseNotFoundException(courseCode);
		}
		
		  

		RegisteredCourse registerCourse = new RegisteredCourse();
		registerCourse.setCourseId(courseCode);
		registerCourse.setstudentId(studentId);
		registerCourse.setGrade(GradeConstant.NOT_GRADED);
		
		registeredCourseRepository.save(registerCourse);
		courseRepository.decrementSeats(courseCode);
		
		return true;
	}

	/**
	 *  Method to drop Course selected by student
	 * @param courseCode
	 * @param studentId
	 * @param registeredCourseList 
	 * @return boolean indicating if the course is dropped successfully
	 * @throws CourseNotFoundException
	 * @throws SQLException 
	 */
	@Override
	
	public boolean dropCourse(String courseCode, String studentId,List<Course> registeredCourseList) throws CourseNotFoundException, SQLException {
		  if(!StudentValidator.isRegistered(courseCode, studentId, registeredCourseList))
	        {
	        	throw new CourseNotFoundException(courseCode);
	        }
		  
		  RegisteredCourseId primaryKey = new RegisteredCourseId();
		  primaryKey.setCourseId(courseCode);
		  primaryKey.setStudentId(studentId);
		  registeredCourseRepository.deleteById(primaryKey);
		  courseRepository.incrementSeats(courseCode);
		  
		  return true;
	}

	/** Method for Fee Calculation for selected courses
	 * Fee calculation for selected courses
	 * @param studentId
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	@Override
	
	public double calculateFee(String studentId) throws SQLException {
		return registrationDAOInterface.calculateFee(studentId);
		//Course Fee is not added
	}


	/**
	 * Method to view grade card for students
	 * @param studentId
	 * @return GradeCard
	 * @throws SQLException 
	 */
	@Override
	
	public GradeCard viewGradeCard(String studentId) throws SQLException {
		return registrationDAOInterface.viewGradeCard(studentId);
//		List<RegisteredCourse> CoursesOfStudent = new ArrayList<RegisteredCourse>();
//		double cgpa=0;
//		try {
//				registeredCourseRepository.findByStudentId(studentId).forEach(course -> CoursesOfStudent.add(course));
//				
//				
//				CoursesOfStudent.forEach(registeredCourse -> {
//					
//					String gradeCon= registeredCourse.getGrade();
//					switch(gradeCon) {
//				    	case "A":
//				    	cgpa+= GradeConstant.A.hasValue();
//				    	break;
//				    	case "A-":
//				    	cgpa+= GradeConstant.A_MINUS.hasValue();
//				    	break;
//				    	case "B":
//				    	cgpa+= GradeConstant.B.hasValue();
//				    	break;
//				    	case "B-":
//				    	cgpa+= GradeConstant.B_MINUS.hasValue();
//				    	break;
//				    	case "C":
//				    	cgpa+= GradeConstant.C.hasValue();
//				    	break;
//				    	case "C-":
//				    	cgpa+= GradeConstant.C_MINUS.hasValue();
//				    	break;
//				    	case "D":
//				    	cgpa+= GradeConstant.D.hasValue();
//				    	break;
//				    	case "E":
//				    	cgpa+= GradeConstant.E.hasValue();
//				    	break;
//				    	case "F":
//				    	cgpa+= GradeConstant.F.hasValue();
//				    	break;
//				    	default:
//				    	cgpa+= GradeConstant.NOT_GRADED.hasValue();
//				    	
//					};
//					
//					logger.info("graded");
//					
//				});
//					
//				}catch(SQLException se) {
//					
//					logger.error(se.getMessage());
//					
//				}
//		
//		GradeCard gradeCard= new GradeCard();
//		gradeCard.setReg_list(CoursesOfStudent);
//		gradeCard.setStudentId(studentId);
//		gradeCard.setCgpa(cgpa/(double)CoursesOfStudent.size());
//		return gradeCard;
	}

	/**
	 *  Method to view the list of available courses
	 * @param studentId
	 * @return List of courses
	 * @throws SQLException 
	 */
	@Override
	
	public List<Course> viewCourses(String studentId) throws SQLException {
		List<Course> AvailableCourses = new ArrayList<Course>();
		registeredCourseRepository.availableCoursesByStudentId(studentId).forEach(course -> {
			Course c = new Course();
			c.setCourseId(course[0].toString());
			c.setSeats((Integer)course[1]);
			c.setCourseName(course[2].toString());
			c.setInstructorId(course[3].toString());
			
			AvailableCourses.add(c);
		});
		return AvailableCourses;
	}

	/**
	 * Method to view the list of courses registered by the student
	 * @param studentId
	 * @return List of courses
	 * @throws SQLException 
	 */
	@Override
	
	public List<Course> viewRegisteredCourses(String studentId) throws SQLException {
		List<Course> RegisteredCourses = new ArrayList<Course>();
		registeredCourseRepository.enrolledCoursesByStudentId(studentId).forEach(course -> {
			Course c = new Course();
			c.setCourseId(course[0].toString());
			c.setSeats((Integer)course[1]);
			c.setCourseName(course[2].toString());
			c.setInstructorId(course[3].toString());
			
			RegisteredCourses.add(c);
		});
		return RegisteredCourses;
	}
    
	/**
	 *  Method to check student registration status
	 * @param studentId
	 * @return boolean indicating if the student's registration status
	 * @throws SQLException
	 */
	@Override

	public boolean getRegistrationStatus(String studentId) throws SQLException {
		java.util.Optional<Student> st = studentRepository.findById(studentId);
		return st.get().isRegistered();
	}
	
	/**
	 * Method to set student registration status
	 * @param studentId
	 * @throws SQLException
	 */
	@Override
	
	public void setRegistrationStatus(String studentId) throws SQLException {
		java.util.Optional<Student> st = studentRepository.findById(studentId);
		st.get().setRegistered(true);
	}

	@Override
	public boolean isReportGenerated(String studentId) throws SQLException {
		java.util.Optional<Student> st = studentRepository.findById(studentId);
		return st.get().isReportGenerated();
	}

	@Override
	public boolean getPaymentStatus(String studentId) throws SQLException 
	{
		java.util.Optional<Student> st = studentRepository.findById(studentId);
		return st.get().isPaid();
	}

	@Override
	public void setPaymentStatus(String studentId) throws SQLException{
		java.util.Optional<Student> st = studentRepository.findById(studentId);
		st.get().setPaid(true);
	}

}