/**
 * 
 */
package com.wibmo.business;

import com.wibmo.exception.RoleMismatchException;
import com.wibmo.exception.UserNotFoundException;

/**
 * @author Himank
 *
 */
public interface UserServiceInterface {

	

	/**
	 * Method to get role of a specific User
	 * @param userId
	 */
	String getRole(String userId);

	/**
	 * Method to verify User credentials
	 * @param userID
	 * @param password
	 * @throws UserNotFoundException 
	 */
	boolean verifyCredentials(String userID, String password) throws UserNotFoundException;
	/**
	 * Method to update password of a user
	 * @param userID
	 * @param newPassword
	 */
	boolean updatePassword(String userID, String newPassword);
	

	void verifyUserRole(String userId, int roleNum) throws RoleMismatchException;
	
}