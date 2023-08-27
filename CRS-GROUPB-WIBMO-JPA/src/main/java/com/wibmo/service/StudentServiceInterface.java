/**
 * 
 */
package com.wibmo.service;

import org.springframework.stereotype.Service;

import com.wibmo.constants.GenderConstant;
import com.wibmo.exception.StudentNotRegisteredException;

/**
 * 
 * @author Himank
 *
 */

@Service
public interface StudentServiceInterface {
	
	/**
	 * Method to register a new student
	 * @param name
	 * @param userID
	 * @param password
	 * @param gender
	 * @param sem
	 * @param branch
	 * @param address
	 * @return
	 * @throws StudentNotRegisteredException 
	 */
	public String register(String name,String userID,String password,GenderConstant gender,int sem,String branch,String address) throws StudentNotRegisteredException;
	
	/**
	 * Method to get Student ID from User ID
	 * @param userId
	 * @return Student ID
	 */
	public String getStudentId(String userId);

	public boolean isApproved(String studentId);
	
}