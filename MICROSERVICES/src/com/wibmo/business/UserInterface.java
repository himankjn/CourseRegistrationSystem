/**
 * 
 */
package com.wibmo.business;

/**
 * @author Himank
 *
 */
public interface UserInterface {

	

	/**
	 * Method to get role of a specific User
	 * @param userId
	 */
	String getRole(String userId);

	/**
	 * Method to verify User credentials
	 * @param userID
	 * @param password
	 */
	boolean verifyCredentials(String userID, String password);
	/**
	 * Method to update password of a user
	 * @param userID
	 * @param newPassword
	 */
	boolean updatePassword(String userID, String newPassword);
	
}