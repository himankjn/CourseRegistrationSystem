/**
 * 
 */
package com.wibmo.dao;

import java.util.ArrayList;  
import java.util.List;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.RegisteredCourse;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.constants.SQLQueriesConstant;
import com.wibmo.exception.CourseExistsAlreadyException;
import com.wibmo.exception.CourseNotDeletedException;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.ProfessorNotAddedException;
import com.wibmo.exception.StudentNotFoundForApprovalException;
import com.wibmo.exception.UserIdAlreadyInUseException;
import com.wibmo.exception.UserNotAddedException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.DBUtils;

/**
 * @author nikita
 *
 */


public class AdminDAOImpl implements AdminDAOInterface{
	private static final Logger logger = Logger.getLogger(AdminDAOImpl.class);
	private static volatile AdminDAOImpl instance = null;
	private PreparedStatement statement = null;
	
	/**
	 * Default Constructor
	 */
	private AdminDAOImpl(){}
	
	/**
	 * Method to make AdminDaoOperation Singleton
	 * @return
	 */
	public static AdminDAOImpl getInstance()
	{
		if(instance == null)
		{
			synchronized(AdminDAOImpl.class){
				instance = new AdminDAOImpl();
			}
		}
		return instance;
	}
	
	Connection connection = DBUtils.getConnection();
	
	/**
	 * Remove Course using SQL commands
	 * @param courseCode
	 * @throws CourseNotFoundException
	 * @throws CourseNotDeletedException 
	 */
	@Override
	public void removeCourse(String courseCode) throws CourseNotFoundException, CourseNotDeletedException{
		
		statement = null;
		try {
			String sql = SQLQueriesConstant.DELETE_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1,courseCode);
			int row = statement.executeUpdate();
			
			logger.info(row + " entries deleted.");
			if(row == 0) {
				logger.info(courseCode + " not in catalog!");
				throw new CourseNotFoundException(courseCode);
			}

			logger.info("Course with courseCode: " + courseCode + " deleted.");
			
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			throw new CourseNotDeletedException(courseCode);
		}
		
	}

	/**
	 * Add Course using SQL commands
	 * @param course
	 * @throws CourseFoundException
	 */
	@Override
	public void addCourse(Course course) throws CourseExistsAlreadyException{
		
		statement = null;
		try {
			
			String sql = SQLQueriesConstant.ADD_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, course.getCourseId());
			statement.setString(2, course.getCourseName());
			
			statement.setInt(3, 10);
			statement.setString(4, "NOT_ASSIGNED");
			int row = statement.executeUpdate();
			
			logger.info(row + " course added");
			if(row == 0) {
				logger.info("Course with courseCode: " + course.getCourseId() + "not added to catalog.");
				throw new CourseExistsAlreadyException(course.getCourseId());
			}
			
			logger.info("Course with courseCode: " + course.getCourseId() + " is added to catalog."); 
			
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			throw new CourseExistsAlreadyException(course.getCourseId());
			
		}
		
	}
	
	/**
	 * Fetch Students yet to approved using SQL commands
	 * @return List of Students yet to approved
	 */
	@Override
	public List<Student> viewPendingAdmissions() {
		
		statement = null;
		List<Student> userList = new ArrayList<Student>();
		try {
			
			String sql = SQLQueriesConstant.VIEW_PENDING_ADMISSION_QUERY;
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				
				Student user = new Student();
				user.setUserId(resultSet.getString(1));
				user.setName(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
				user.setRole(RoleConstant.stringToName(resultSet.getString(4)));
				user.setGender(GenderConstant.stringToGender( resultSet.getString(5)));
				user.setAddress(resultSet.getString(6));
				user.setStudentId(resultSet.getString(7));
				userList.add(user);
				
			}
			
			logger.info(userList.size() + " students have pending-approval.");
			
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			
		}
		
		return userList;
		
	}

	/**
	 * Approve Student using SQL commands
	 * @param studentId
	 * @throws StudentNotFoundException
	 */
	@Override
	public void approveSingleStudent(String studentId) throws StudentNotFoundForApprovalException {
		
		statement = null;
		try {
			String sql = SQLQueriesConstant.APPROVE_STUDENT_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1,studentId);
			int row = statement.executeUpdate();
			
			logger.info(row + " student approved.");
			if(row == 0) {
				//logger.info("Student with studentId: " + studentId + " not found.");
				throw new StudentNotFoundForApprovalException(studentId);
			}
			
			logger.info("Student with studentId: " + studentId + " approved by admin.");
			
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			
		}
		
	}

	
	public void approveAllStudents(List<Student> studentList) {
		statement = null;
		try {
			String sql = SQLQueriesConstant.APPROVE_ALL_STUDENT_QUERY;
			statement = connection.prepareStatement(sql);
			int row = statement.executeUpdate();
			logger.info(row + " student approved.");
		}catch(SQLException se) {
			logger.info(se.getMessage());
		}
	}
	/**
	 * Method to add user using SQL commands
	 * @param user
	 * @throws UserNotAddedException
	 * @throws UserIdAlreadyInUseException 
	 */
	@Override
	public void addUser(User user) throws UserNotAddedException, UserIdAlreadyInUseException{
		
		statement = null;
		try {
			
			String sql = SQLQueriesConstant.ADD_USER_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getUserId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getRole().toString());
			statement.setString(5, user.getGender().toString());
			statement.setString(6, user.getAddress());
			
			int row = statement.executeUpdate();
			
			logger.info(row + " user added.");
			if(row == 0) {
				logger.info("User with userId: " + user.getUserId() + " not added.");
				throw new UserNotAddedException(user.getUserId()); 
			}

			logger.info("User with userId: " + user.getUserId() + " added."); 
			
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			throw new UserIdAlreadyInUseException(user.getUserId());
			
		}
		
	}

	/**
	 * Add professor using SQL commands
	 * @param professor
	 * @throws UserIdAlreadyInUseException 
	 * @throws ProfessorNotAddedException 
	 */
	@Override
	public void addProfessor(Professor professor) throws UserIdAlreadyInUseException, ProfessorNotAddedException {
		
		try {
			
			this.addUser(professor);
			
		}catch (UserNotAddedException e) {
			
			logger.info(e.getMessage());
			throw new ProfessorNotAddedException(professor.getUserId());
			
		}catch (UserIdAlreadyInUseException e) {
			
			logger.info(e.getMessage());
			throw e;
			
		}
		
		
		statement = null;
		try {
			
			String sql = SQLQueriesConstant.ADD_PROFESSOR_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, professor.getUserId());
			statement.setString(2, professor.getUserId());
			statement.setString(3, professor.getDepartment());
			statement.setString(4, professor.getDesignation());
			int row = statement.executeUpdate();

			logger.info(row + " professor added.");
			if(row == 0) {
				logger.info("Professor with professorId: " + professor.getUserId() + " not added.");
				throw new ProfessorNotAddedException(professor.getUserId());
			}
			
			logger.info("I'm here! Professor with professorId: " + professor.getUserId() + " added."); 
			
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			throw new UserIdAlreadyInUseException(professor.getUserId());
			
		} 
		
	}
	
	/**
	 * Assign courses to Professor using SQL commands
	 * @param courseCode
	 * @param professorId
	 * @throws CourseNotFoundException
	 * @throws UserNotFoundException 
	 */
	@Override
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException{
		
		statement = null;
		try {
			String sql = SQLQueriesConstant.ASSIGN_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			
			statement.setString(1,professorId);
			statement.setString(2,courseCode);
			int row = statement.executeUpdate();
			
			if(row == 0) {
				logger.info(courseCode + " not found");
				throw new CourseNotFoundException(courseCode);
			}
			logger.info("Course with courseCode: " + courseCode + " is assigned to professor with professorId: " + professorId + ".");
		}catch(SQLException se) {
			logger.info(se.getMessage());
			
		}
		
	}
	
	/**
	 * View courses in the catalog
	 * @param Catalog ID
	 * @return List of courses in the catalog
	 */
	public List<Course> viewCourses() {
		
		statement = null;
		List<Course> courseList = new ArrayList<>();
		try {
			
			String sql = SQLQueriesConstant.VIEW_COURSE_QUERY;
			statement = connection.prepareStatement(sql);
			//statement.setInt(1, catalogId);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				Course course = new Course();
				course.setCourseId(resultSet.getString(1));
				course.setCourseName(resultSet.getString(2));
				course.setInstructorId(resultSet.getString(3));
				courseList.add(course);
				
			}
			
			logger.info("Number of courses in the Catalog are : " + courseList.size());
			
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			
		}
		
		return courseList; 
		
	}
	
	/**
	 * View professor in the institute
	 * @return List of the professors in the institute  
	 */
	@Override
	public List<Professor> viewProfessors() {
		
		statement = null;
		List<Professor> professorList = new ArrayList<Professor>();
		try {
			
			String sql = SQLQueriesConstant.VIEW_PROFESSOR_QUERY;
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				Professor professor = new Professor();
				professor.setUserId(resultSet.getString(1));
				professor.setName(resultSet.getString(2));
				professor.setGender(GenderConstant.stringToGender(resultSet.getString(3)));
				professor.setDepartment(resultSet.getString(4));
				professor.setDesignation(resultSet.getString(5));
				professor.setAddress(resultSet.getString(6));
				professor.setRole(RoleConstant.PROFESSOR);
				professor.setPassword("*********");
				professorList.add(professor);
				
			}
		}catch(SQLException se) {
			
			logger.info(se.getMessage());
			
		}
		return professorList;
	}
	
	public void setGeneratedReportCardTrue(String Studentid)
	{
		String sql1 = SQLQueriesConstant.SET_GENERATED_REPORT_CARD_TRUE;
		try {
		statement = connection.prepareStatement(sql1);
		statement.setString(1, Studentid);
		int row = statement.executeUpdate();
		}
		catch(SQLException e)
		{
			logger.info(e.getMessage());
		}
	}

	@Override
	public List<RegisteredCourse> generateGradeCard(String Studentid) 
	{
		List<RegisteredCourse> CoursesOfStudent = new ArrayList<RegisteredCourse>();
		
		try {
					String sql = SQLQueriesConstant.VIEW_REGISTERED_COURSES;
					statement = connection.prepareStatement(sql);
					statement.setString(1, Studentid);
					ResultSet resultSet = statement.executeQuery();
					
					while(resultSet.next()) {
						
						Course course = new Course();
						RegisteredCourse temp = new RegisteredCourse() ;
						course.setCourseId(resultSet.getString(1));
						course.setCourseName(resultSet.getString(2));
						course.setInstructorId(resultSet.getString(3));
						course.setSeats(resultSet.getInt(4));
						
						
						temp.setCourse(course);
						logger.info("course object generated");
						temp.setstudentId(Studentid);
						
						
						temp.setGrade(resultSet.getString(8));
						
						logger.info("graded");
						CoursesOfStudent.add(temp);
						
					}
					
					String sql1 = SQLQueriesConstant.SET_GENERATED_REPORT_CARD_TRUE;
					statement = connection.prepareStatement(sql1);
					statement.setString(1, Studentid);
					int row = statement.executeUpdate();
						
					
				}catch(SQLException se) {
					
					logger.info(se.getMessage());
					
				}
		
		return CoursesOfStudent;
		
		
	}
	
	public List<String> getProfCourseRequests(String courseId){
		String sql = SQLQueriesConstant.GET_PROF_COURSE_REQUESTS;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, courseId);
			ResultSet rs = statement.executeQuery();
			List<String> profIDs= new ArrayList<String>();
			while(rs.next()) {
				String userId=rs.getString("userId");
				profIDs.add(userId);
			}
			return profIDs;
		}
		catch(SQLException e)
		{
			logger.info(e.getMessage());
		}
		return null;
	}

	
	
	

}
