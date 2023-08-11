/**
 * 
 */
package com.wibmo.business;

/**
 * 
 * @author Himank
 *
 */
public interface StudentInterface {
	
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
	 */
	public String register(String name,String userID,String password,String gender,int sem,String branch,String address);
	
	/**
	 * Method to get Student ID from User ID
	 * @param userId
	 * @return Student ID
	 */
	public String getStudentId(String userId);
	
}