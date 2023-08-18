package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wibmo.constants.GradeConstant;
import com.wibmo.constants.SQLQueriesConstant;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.utils.DBUtils;

import com.wibmo.bean.Course;
import com.wibmo.bean.Grade;
import com.wibmo.bean.GradeCard;
import com.wibmo.bean.RegisteredCourse;


/**
 * 
 * @author Bhuvan
 * Class to implement Registration Dao Operations
 * This class communicates with the database.
 *
 */
public class RegistrationDAOImpl implements RegistrationDAOInterface{
	private static final Logger logger = Logger.getLogger(RegistrationDAOImpl.class);
	private static volatile RegistrationDAOImpl instance=null;
	private PreparedStatement stmt = null;
	
	/**
	 * Default Constructor
	 */
	private RegistrationDAOImpl() {}
	
	/**
	 * Method to make RegistrationDaoOperation Singleton
	 * @return
	 */
	public static RegistrationDAOImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(RegistrationDAOImpl.class){
				instance=new RegistrationDAOImpl();
			}
		}
		return instance;
	}


	@Override
	public boolean addCourse(String courseId, String studentId) throws SQLException{
		
		Connection conn = DBUtils.getConnection();
		
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.ADD_COURSE);
			stmt.setString(1, studentId);
			stmt.setString(2, courseId);
			stmt.setString(3, "-");
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement(SQLQueriesConstant.DECREMENT_COURSE_SEATS);
			stmt.setString(1, courseId);
			stmt.executeUpdate();
			return true;
		}
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		}
		finally
		{
			stmt.close();
		}
		return false;
		
	}
	
	
	/**
	 * Number of registered courses for a student
	 * @param studentId
	 * @return Number of registered courses for a student
	 * @throws SQLException 
	 */
	@Override
	public int numOfRegisteredCourses(String studentId) throws SQLException{
		
		Connection conn = DBUtils.getConnection();
		
		int count = 0;
		try {

			stmt = conn.prepareStatement(SQLQueriesConstant.NUMBER_OF_REGISTERED_COURSES);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count++;
			}
			return count;

		}
		catch (SQLException se) 
		{

			logger.error(se.getMessage());

		} 
		catch (Exception e)
		{

			logger.error(e.getMessage());
		}
		finally
		{
			stmt.close();
		}
		
		return count;
	}


	/**
	 * Check if seat is available for that particular course
	 * @param courseId
	 * @return status of seat availablity
	 * @throws SQLException 
	 */
	@Override
	public boolean seatAvailable(String courseId) throws SQLException {

		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.GET_SEATS);
			stmt.setString(1, courseId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return (rs.getInt("seats") > 0);
			}

		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		finally
		{
			stmt.close();
		}
		
		return true;
		

	}
	


	/**
	 * Method checks if the student is registered for that course
	 * @param courseId
	 * @param studentId
	 * @return Students registration status
	 * @throws SQLException 
	 */
	@Override
	public boolean isRegistered(String courseId, String studentId) throws SQLException{
		
		Connection conn = DBUtils.getConnection();
		
		boolean check = false;
		try
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.IS_REGISTERED);
			stmt.setString(1, courseId);
			stmt.setString(2, studentId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				check = true;
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			stmt.close();
		}
		
		return check;
		
	}


	/**
	 * Drop Course selected by student
	 * @param courseId : code for selected course
	 * @param studentId
	 * @return status of drop course operation
	 * @throws CourseNotFoundException 
	 */
	@Override
	public boolean dropCourse(String courseId, String studentId) throws SQLException {
	
		Connection conn = DBUtils.getConnection();
		
		
			try
			{
				stmt = conn.prepareStatement(SQLQueriesConstant.DROP_COURSE_QUERY);
				stmt.setString(1, courseId);
				stmt.setString(2, studentId);
				stmt.execute();
				
				stmt = conn.prepareStatement(SQLQueriesConstant.INCREMENT_SEAT_QUERY);
				stmt.setString(1, courseId);
				stmt.execute();
				
				stmt.close();
				
				return true;
			}
			catch(Exception e)
			{
				logger.error(e.getMessage());
			}
			finally
			{
	
				stmt.close();
			}
			
		
		return false;
		
	}
	
	/**
	 * Method to retrieve fee for the selected courses from the database and calculate total fee
	 * @param studentId
	 * @return Fee Student has to pay
	 * @throws SQLException 
	 */
	
	@Override
	public double calculateFee(String studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		double fee = 0;
		try
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.CALCULATE_FEES);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			fee = rs.getDouble(1);
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			stmt.close();
		}
		
		return fee;
	}

	/**
	 * Method to view grade card of the student
	 * @param studentId
	 * @throws SQLException 
	 * @return Studen's grade card
	 */
	@Override
	public GradeCard viewGradeCard(String studentId) throws SQLException {
		
			List<RegisteredCourse> CoursesOfStudent = new ArrayList<RegisteredCourse>();
			double cgpa=0;
			try {
					Connection conn = DBUtils.getConnection();
					String sql = SQLQueriesConstant.VIEW_REGISTERED_COURSES;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, studentId);
					ResultSet resultSet = stmt.executeQuery();
					
					
					while(resultSet.next()) {
						
						Course course = new Course();
						RegisteredCourse temp = new RegisteredCourse() ;
						course.setCourseId(resultSet.getString("courseId"));
						course.setCourseName(resultSet.getString("courseName"));
						course.setInstructorId(resultSet.getString("professorId"));
						course.setSeats(resultSet.getInt("seats"));
						
						
						temp.setCourse(course);
						logger.info("course object generated");
						temp.setstudentId(studentId);
						
						String gradeCon=resultSet.getString("grade");
						switch(gradeCon) {
					    	case "A": temp.setGrade(GradeConstant.A);
					    	cgpa+= GradeConstant.A.hasValue();
					    	break;
					    	case "A-": temp.setGrade(GradeConstant.A_MINUS);
					    	cgpa+= GradeConstant.A_MINUS.hasValue();
					    	break;
					    	case "B": temp.setGrade(GradeConstant.B);
					    	cgpa+= GradeConstant.B.hasValue();
					    	break;
					    	case "B-": temp.setGrade(GradeConstant.B_MINUS);
					    	cgpa+= GradeConstant.B_MINUS.hasValue();
					    	break;
					    	case "C":temp.setGrade(GradeConstant.C);
					    	cgpa+= GradeConstant.C.hasValue();
					    	break;
					    	case "C-": temp.setGrade(GradeConstant.C_MINUS);
					    	cgpa+= GradeConstant.C_MINUS.hasValue();
					    	break;
					    	case "D": temp.setGrade(GradeConstant.D);
					    	cgpa+= GradeConstant.D.hasValue();
					    	break;
					    	case "E": temp.setGrade(GradeConstant.E);
					    	cgpa+= GradeConstant.E.hasValue();
					    	break;
					    	case "F": temp.setGrade(GradeConstant.F);
					    	cgpa+= GradeConstant.F.hasValue();
					    	break;
					    	default: temp.setGrade(GradeConstant.NOT_GRADED);
					    	cgpa+= GradeConstant.NOT_GRADED.hasValue();
					    	
						}
						
						
						logger.info("graded");
						CoursesOfStudent.add(temp);
						
					}	
						
					}catch(SQLException se) {
						
						logger.error(se.getMessage());
						
					}
			
			GradeCard gradeCard= new GradeCard();
			gradeCard.setReg_list(CoursesOfStudent);
			gradeCard.setStudentId(studentId);
			gradeCard.setCgpa(cgpa/(double)CoursesOfStudent.size());
			return gradeCard;
	}

	/**
	 * Method to get the list of courses available from course catalog 
	 * @param studentId
	 * @return list of courses
	 * @throws SQLException
	 */
	@Override
	public List<Course> viewCourses(String studentId) throws SQLException {
		
		List<Course> availableCourseList = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.VIEW_AVAILABLE_COURSES);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				availableCourseList.add(new Course(rs.getString("courseId"), rs.getString("courseName"),
						rs.getString("professorId"), rs.getInt("seats")));

			}
			

		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
		} 
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		
		return availableCourseList;
		
	}

	/**
	 * Method to get the list of courses registered by the student
	 * @param studentId
	 * @return list of courses registered by student
	 * @throws SQLException 
	 */
	@Override
	public List<Course> viewRegisteredCourses(String studentId) throws SQLException {

		Connection conn = DBUtils.getConnection();
		List<Course> registeredCourseList = new ArrayList<>();
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.VIEW_REGISTERED_COURSES);
			stmt.setString(1, studentId);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				registeredCourseList.add(new Course(rs.getString("courseId"), rs.getString("courseName"),
						rs.getString("professorId"), rs.getInt("seats")));

			}
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());

		} 
		finally
		{
			stmt.close();
		}
		
		return registeredCourseList;
	}

	/**
	 * Method to retrieve Student's registration status
	 * @param studentId
	 * @return Student's registration status
	 * @throws SQLException
	 */
	@Override
	public boolean getRegistrationStatus(String studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.GET_REGISTRATION_STATUS);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);
			//logger.info(status);	
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());

		} 
		finally
		{
			stmt.close();		}

		return status;
	}
	/**
	 * Method to set Student's registration status
	 * @param studentId
	 * @throws SQLException
	 */
	@Override
	public void setRegistrationStatus(String studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.SET_REGISTRATION_STATUS);
			stmt.setString(1, studentId);
			stmt.executeUpdate();

		} 
		catch (SQLException e) 
		{

		} 
		finally
		{
			stmt.close();
		}

	}

	@Override
	public boolean isReportGenerated(String studentId) throws SQLException
	{
		Connection conn = DBUtils.getConnection();
		boolean status = false;
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.GET_GENERATED_REPORT_CARD_TRUE);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			status = rs.getBoolean(1);
			//logger.info(status);	
		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());
	
		} 
		finally
		{
			stmt.close();
		}
	
		return status;
	}

	@Override
	public boolean getPaymentStatus(String studentId) throws SQLException 
	{
		{
			Connection conn = DBUtils.getConnection();
			boolean status = false;
			try 
			{
				stmt = conn.prepareStatement(SQLQueriesConstant.GET_PAYMENT_STATUS);
				stmt.setString(1, studentId);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				status = rs.getBoolean(1);
				//logger.info(status);	
			} 
			catch (SQLException e) 
			{
				logger.error(e.getMessage());

			} 
			finally
			{
				stmt.close();
			}

			return status;
	}


	}

	@Override
	public void setPaymentStatus(String studentId) throws SQLException {
		Connection conn = DBUtils.getConnection();
		try 
		{
			stmt = conn.prepareStatement(SQLQueriesConstant.SET_PAYMENT_STATUS);
			stmt.setString(1, studentId);
			stmt.executeUpdate();

		} 
		catch (SQLException e) 
		{
			logger.error(e.getMessage());

		} 
		finally
		{
			stmt.close();
		}

	}
}