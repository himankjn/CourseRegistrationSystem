/**
 * 
 */
package com.wibmo.dao;

import com.wibmo.bean.Student;
import com.wibmo.exception.StudentNotRegisteredException;

/**
 * @author Shanmukh
 *
 */
public interface StudentDAOInterface {
	
	
	/**
	 * Method to add student to database
	 * @param student: student object containing all the fields
	 * @return true if student is added, else false
	 * @throws StudentNotRegisteredException
	 */
	public String addStudent(Student student) throws StudentNotRegisteredException;
	
	
	/**
	 * Method to retrieve Student Id from User Id
	 * @param userId
	 * @return Student Id
	 */
	public String getStudentId(String userId);
	
	/**
	 * Method to check if Student is approved
	 * @param studentId
	 * @return boolean indicating if student is approved
	 */
	public boolean isApproved(String studentId);
}
