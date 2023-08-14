/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.GradeCard;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;

/**
 * @author Shanmukh
 *
 */
public interface AdminDaoInterface {
	
	public List<Course>viewCourses();
	public List<Professor> viewProfessors();
	
	public GradeCard generateGradeCard(String Studentid);
	public List<Student> viewPendingAdmissions();
	public void approveStudent(String studentId);
	public void addProfessor(Professor professor);
	public void removeCourse(String courseCode);
	public void addCourse(Course course);
	public void assignCourse(String courseCode,String professorId);
	public void addUser(User user);
}
