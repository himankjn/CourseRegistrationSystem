
package com.wibmo.exception;

/**
 * Exception to check if user exists 
 * @author nikita
 *
 */
public class UserNotFoundException extends Exception {

	private String userId;

	/***
	 * Setter function for UserId
	 * @param userId
	 */
	public UserNotFoundException(String id) {
		userId = id;
	}

	/**
	 * Message thrown by exception
	 */
	
	public String getMessage() {
		return "User with userId: " + userId + " not found.";
	}
	

}