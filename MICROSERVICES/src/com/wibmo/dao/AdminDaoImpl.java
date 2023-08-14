/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.GradeCard;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.constants.SQLQueriesConstant;
import com.wibmo.utils.DBUtils;

/**
 * @author Shanmukh
 *
 */
public class AdminDaoImpl implements AdminDaoInterface{

	private static volatile AdminDaoImpl instance=null;

	/**
	 * Default Constructor
	 */
	private AdminDaoImpl()
	{
		
	}
	
	/**
	 * Method to make ProfessorDaoOperation Singleton
	 * @return
	 */
	public static AdminDaoImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(AdminDaoImpl.class){
				instance=new AdminDaoImpl();
			}
		}
		return instance;
	}
	
	@Override
	public List<Course> viewCourses() {
		// TODO Auto-generated method stub
		Connection connection = DBUtils.getConnection();
		List<Course> courseCatalogue = new ArrayList<Course>();
		
		try {
			PreparedStatement stmt = connection.prepareStatement(SQLQueriesConstant.GET_COURSE_CATALOUGE);
			
			ResultSet results = stmt.executeQuery();
			while(results.next()) {
				courseCatalogue.add(new Course(results.getString(1),results.getInt(2),results.getString(3),results.getString(4)));
			}
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}finally {
			try {
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return courseCatalogue;
		
	}

	@Override
	public List<Professor> viewProfessors() {
		// TODO Auto-generated method stub
		Connection connection = DBUtils.getConnection();
		List<Professor> professorList = new ArrayList<Professor>();
		
		try {
			PreparedStatement stmt = connection.prepareStatement(SQLQueriesConstant.GET_PROFESSORS);
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				professorList.add(new Professor(results.getInt("userId"),results.getString("name"),results.getString("password"),results.getString("address"),results.getString("professorId"),results.getString("department"),results.getString(results.getString("position"))));
			}
			
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}finally {
			try {
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return professorList;
	}

	@Override
	public GradeCard generateGradeCard(String Studentid) {
		// TODO Auto-generated method stub
		Connection connection = DBUtils.getConnection();
		int courseGrade,courseCredits;
		GradeCard gradeCard = new GradeCard();
		double cgpa = 0;
		double coursesEnrolled = 0;
		int totalCredits = 0;
		try {
			PreparedStatement stmt = connection.prepareStatement(SQLQueriesConstant.GET_GRADES);
			stmt.setString(1,Studentid);
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				courseCredits = results.getInt(1);
				courseGrade = results.getInt(2);
				coursesEnrolled++;
				totalCredits+=courseCredits*courseGrade;
			}
			
			cgpa = totalCredits/coursesEnrolled;
			
			gradeCard.setStudentId(Studentid);
			gradeCard.setCgpa(cgpa);
			gradeCard.setSemester(1);
			
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}finally {
			try {
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return gradeCard;
	}

	@Override
	public List<Student> viewPendingAdmissions() {
		// TODO Auto-generated method stub
		Connection connection=DBUtils.getConnection();
		List<Student> pendingAdmissionedStudents=new ArrayList<Student>();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_PENDING_ADMINSSIONED_STUDENTS);
			
			ResultSet results = statement.executeQuery();
			while(results.next())
			{
				pendingAdmissionedStudents.add(new Student(results.getInt("userId"),results.getString("name"),results.getString("password"),results.getString("address"),
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
		return null;
	}

	@Override
	public void approveStudent(String studentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProfessor(Professor professor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCourse(String courseCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCourse(Course course) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignCourse(String courseCode, String professorId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
	}

}
