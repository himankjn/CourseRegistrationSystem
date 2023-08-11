/**
 * 
 */
package com.wibmo.dao;

/**
 * @author Shanmukh
 *
 */
public interface ProfessorDaoInterface {
	public List<Course> getCoursesByProfessor(String userId);
	
	public List<EnrolledStudent> getEnrolledStudents(String courseId);
	public Boolean addGrad(String studentId,String courseCode,String grade);
	public String getProfessorById(String profId);
}
