/**
 * 
 */
package com.wibmo.dao;

/**
 * @author Shanmukh
 *
 */
public interface AdminDaoInterface {
	
	public List<Course>viewCourses();
	public List<Professor> viewProfessors();
	
	public List<RegisteredCourse> generateGradeCard(String Studentid);
	public List<Student> viewPendingAdmissions();
	public void approveStudent(String studentId);
	public void addProfessor(Professor professor);
	public void removeCourse(String courseCode);
	public void addCourse(Course course);
	public void assignCourse(String courseCode,String professorId);
	public void addUser(User user);
}
