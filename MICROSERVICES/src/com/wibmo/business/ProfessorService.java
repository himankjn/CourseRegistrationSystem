package com.wibmo.business;

import java.util.ArrayList;
import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;
import com.wibmo.dao.ProfessorDaoInterface;
import com.wibmo.dao.ProfessorDaoImpl;

/**
 * @author Himank
 */
public class ProfessorService implements ProfessorInterface {
	
	private static volatile ProfessorService instance=null;
	ProfessorDaoInterface professorDAOInterface=ProfessorDaoImpl.getInstance();
	private ProfessorService()
	{

	}

	public static ProfessorService getInstance()
	{
		if(instance==null)
		{
			synchronized(ProfessorService.class){
				instance=new ProfessorService();
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
	@Override
	
	public boolean submitGrade(String studentId,String courseCode,String grade){
		return false;
	}
	
	
	/**
	 * Method to view all the enrolled students
	 * @param courseId: Course id 
	 */
	
	@Override
	public List<Student> viewEnrolledStudents(String profId) {
		List<Student> enrolledStudents=new ArrayList<Student>();
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
	
	@Override
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