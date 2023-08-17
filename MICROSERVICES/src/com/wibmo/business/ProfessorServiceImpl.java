package com.wibmo.business;

import java.util.ArrayList;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;
import com.wibmo.bean.Student;
import com.wibmo.dao.ProfessorDAOImpl;
import com.wibmo.dao.ProfessorDAOInterface;

/**
 * @author Himank
 */
public class ProfessorServiceImpl implements ProfessorServiceInterface {
	
	private static volatile ProfessorServiceImpl instance=null;
	ProfessorDAOInterface professorDAOInterface=ProfessorDAOImpl.getInstance();
	private ProfessorServiceImpl()
	{

	}

	public static ProfessorServiceImpl getInstance()
	{
		if(instance==null)
		{
			synchronized(ProfessorServiceImpl.class){
				instance=new ProfessorServiceImpl();
			}
		}
		return instance;
	}
	
	
	/**
	 * Method to submit student grade for a course;
	 * @param studentId
	 * @param courseCode
	 * @param grade
	 */
	public boolean submitGrade(String studentId,String courseCode,String grade){
		professorDAOInterface.addGrade(studentId, courseCode, grade);
		return true;
	}
	
	
	/**
	 * Method to view all the enrolled students
	 * @param coursxeId: Course id 
	 */
	
	public List<EnrolledStudent> viewEnrolledStudents(String profId) {
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try
		{
			enrolledStudents=professorDAOInterface.getEnrolledStudents(profId);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}
		return enrolledStudents;
	}

	
	/**
	 * Method to get list of all course for a professor
	 * @param profId: professor id
	 */
	
	public List<Course> viewCourses(String profId) {
		List<Course> coursesOffered=new ArrayList<Course>();
		try
		{
			coursesOffered=professorDAOInterface.getCoursesByProfessor(profId);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}
		return coursesOffered;
	}
	
}