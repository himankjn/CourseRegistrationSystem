
package com.wibmo.exception;

/**
 * Exception to check if user exists 
 * @author nikita
 *
 */
public class RoleMismatchException extends Exception {

	private String userId;
	private String role;

	/***
	 * Setter function for UserId
	 * @param userId
	 */
	public RoleMismatchException(String userId, String role) {
		this.userId = userId;
		this.role=role;
	}

	/**
	 * Message thrown by exception
	 */
	
	public String getMessage() {
		return "Role entered is not consistent with the database!";
	}
	

}