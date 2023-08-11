/**
 * 
 */
package com.wibmo.business;

/**
 * 
 */

import java.util.List;


/**
 * @author Himank
 *
 */
public interface AdminInterface 
{
	
	/**
	 * view courses in a catalogue
	 * @param catalogId
	 * @return List of courses in catalog
	 */
	public List<Course> viewCourses(int catalogId);
	
	/**
	 * View professor in the institute
	 * @return List of the professors in the institute  
	 */
	public List<Professor> viewProfessors();
	
	/**
	 * Method to view Students of the institute
	 * @return List of Students with pending admissions
	 */
	public List<Student> viewStudents();
	
	/**
	 * Method to generate grade card of a Student 
	 * @param studentid 
	 * @return 
	 */
	public List<RegisteredCourse> generateGradeCard(String Studentid);
	
	
	/**
	 * Method to add Professor to DB
	 * professor : Professor Object storing details of a professor 
	 */
	
	public void addProfessor(Professor professor);
	
	/**
	 * Method to Delete Course from Course Catalog
	 * @param courseCode
	 * @param catalogueId
	 */

	public void removeCourse(String coursecode, int catalogId);
	
	/**
	 * Method to add Course to Course Catalog
	 * @param course : Course object storing details of a course
	 * @param catalogueId
	 */
	
	public void addCourse(Course course,int catalogId);
	
	/**
	 * Method to assign Course to a Professor
	 * @param courseCode
	 * @param professorId 
	 */
	public void assignCourse(String courseCode, String professorId);
	
}