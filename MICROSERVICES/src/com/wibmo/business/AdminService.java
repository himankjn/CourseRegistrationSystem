/**
 * 
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.GradeCard;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.dao.AdminDaoImpl;
import com.wibmo.dao.AdminDaoInterface;

/**
 * @author Shanmukh
 *
 */
public class AdminService implements AdminInterface{

	
	private static volatile AdminService instance=null;
	AdminDaoInterface adminDAOInterface=AdminDaoImpl.getInstance();
	private AdminService()
	{

	}

	public static AdminService getInstance()
	{
		if(instance==null)
		{
			synchronized(AdminService.class){
				instance=new AdminService();
			}
		}
		return instance;
	}
	
	
	@Override
	public List<Course> viewCourses(int catalogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Professor> viewProfessors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> viewStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GradeCard generateGradeCard(String Studentid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProfessor(Professor professor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCourse(String coursecode, int catalogId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCourse(Course course, int catalogId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignCourse(String courseCode, String professorId) {
		// TODO Auto-generated method stub
		
	}

}
