package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;
import com.wibmo.constants.SQLQueriesConstant;
import com.wibmo.utils.DBUtils;



/**
 * @author himank
 */
public class ProfessorDaoImpl implements ProfessorDaoInterface {

	private static volatile ProfessorDaoImpl instance=null;

	/**
	 * Default Constructor
	 */
	private ProfessorDaoImpl()
	{
		
	}
	
	/**
	 * Method to make ProfessorDaoOperation Singleton
	 * @return
	 */
	public static ProfessorDaoImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(ProfessorDaoImpl.class){
				instance=new ProfessorDaoImpl();
			}
		}
		return instance;
	}
	
	
	/**
	 * Method to get Courses by Professor Id 
	 * @param userId, prof id of the professor
	 * @return get the courses offered by the professor.
	 */
	@Override
	public List<Course> getCoursesByProfessor(String profId) {
		Connection connection=DBUtils.getConnection();
		List<Course> courseList=new ArrayList<Course>();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_COURSES);
			
			statement.setString(1, profId);
			
			ResultSet results=statement.executeQuery();
			while(results.next())
			{
				courseList.add(new Course(results.getString("courseId"),results.getInt("credits"), results.getString("courseName"),results.getString("professorId")));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
		finally
		{
			try {
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}
		return courseList;
		
	}

	/**
	 * Method to view list of enrolled Students
	 * @param: courseCode: course code of the professor
	 * @return: return the enrolled students for the corresponding professor and course code.
	 */
	@Override
	public List<Student> getEnrolledStudents(String courseId) {
		Connection connection=DBUtils.getConnection();
		List<Student> enrolledStudents=new ArrayList<Student>();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_ENROLLED_STUDENTS);
			statement.setString(1, courseId);
			
			ResultSet results = statement.executeQuery();
			while(results.next())
			{
				enrolledStudents.add(new Student(results.getInt("userId"),results.getString("name"),results.getString("password"),results.getString("address"),
						results.getString("studentId"),results.getString("department")));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
		finally
		{
			try {
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return enrolledStudents;
	}
	
	/**
	 * Method to submit grade for a student for a course.
	 * @param: profId: professor id 
	 * @param: courseCode: course code for the corresponding course
	 * @param grade : The grade for the student
	 */
	public Boolean addGrade(String studentId,String courseCode,String grade) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.ADD_GRADE);
			
			statement.setString(1, grade);
			statement.setString(2, courseCode);
			statement.setString(3, studentId);
			
			int row = statement.executeUpdate();
			
			if(row==1)
				return true;
			else
				return false;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	
	}


}