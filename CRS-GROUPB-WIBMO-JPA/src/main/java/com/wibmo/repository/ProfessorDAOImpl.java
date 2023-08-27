package com.wibmo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wibmo.constants.SQLQueriesConstant;
import com.wibmo.entity.Course;
import com.wibmo.entity.EnrolledStudent;
import com.wibmo.utils.DBUtils;

/**
 * @author bhuvan
 */

@Repository
public class ProfessorDAOImpl implements ProfessorDAOInterface {
	private static final Logger logger = LogManager.getLogger(ProfessorDAOImpl.class);
	
	/**
	 * Method to get Courses by Professor Id using SQL Commands
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
				Course course=new Course();
				course.setCourseId(results.getString("courseId"));
				course.setCourseName(results.getString("courseName"));
				course.setInstructorId(results.getString("professorId"));
				course.setSeats(results.getInt("seats"));
				courseList.add(course);
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return courseList;
		
	}

	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseId: course code of the professor
	 * @return: return the enrolled students for the corresponding professor and course code.
	 */
	@Override
	public List<EnrolledStudent> getEnrolledStudents(String courseId) {
		Connection connection=DBUtils.getConnection();
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_ENROLLED_STUDENTS);
			statement.setString(1, courseId);
			
			ResultSet results = statement.executeQuery();
			while(results.next())
			{
				enrolledStudents.add(new EnrolledStudent(results.getString("courseId"),results.getString("courseName"),results.getString("studentId")));
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return enrolledStudents;
	}
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseId: course code for the corresponding 
	 * @return: returns the status after adding the grade
	 */
	public Boolean addGrade(String studentId,String courseId,String grade) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.ADD_GRADE);
			
			statement.setString(1, grade);
			statement.setString(2, courseId);
			statement.setString(3, studentId);
			
			int row = statement.executeUpdate();
			
			if(row==1)
				return true;
			else
				return false;
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		return false;
	}
	

	/**
	 * Method to Get professor name by id
	 * @param profId
	 * @return Professor Id in string
	 */
	@Override
	public String getProfessorById(String profId)
	{
		String prof_Name = null;
		Connection connection=DBUtils.getConnection();
		try 
		{
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_PROF_NAME);
			
			statement.setString(1, profId);
			ResultSet rs = statement.executeQuery();
			rs.next();
			
			prof_Name = rs.getString(1);
			
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		
		return prof_Name;
	}


	@Override
	public boolean sendCourseAssignmentRequest(String userId, String courseId) {
		Connection connection=DBUtils.getConnection();
		try 
		{
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.REQUEST_COURSE_ASSIGNMENT);
			
			statement.setString(1, userId);
			statement.setString(2,courseId);
			int rs = statement.executeUpdate();
			if(rs>0)return true;
			else return false;
			
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
	

		return false;
	}
	
	@Override
    public List<Course> getUnassignedCourses() {
        List<Course> unassignedCourses = new ArrayList<Course>();
        Connection connection=DBUtils.getConnection();
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_UNASSIGNED_COURSES);
            statement.setString(1, "NOT_ASSIGNED");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
            	Course course=new Course();
				course.setCourseId(rs.getString("courseId"));
				course.setCourseName(rs.getString("courseName"));
				course.setInstructorId(rs.getString("professorId"));
				course.setSeats(rs.getInt("seats"));
                unassignedCourses.add(course);
            }
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage());
        }
        return unassignedCourses;
    }
}