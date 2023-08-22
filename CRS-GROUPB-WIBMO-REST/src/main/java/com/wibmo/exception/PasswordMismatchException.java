/**
 * 
 */
package com.wibmo.exception;

/**
 * @author nikita
 *
 */
public class PasswordMismatchException extends Exception{
	
	private String password;
	private String confirmPassword;
	
	public PasswordMismatchException(String password, String confirmPassword) {
		this.setPassword(password);
		this.setConfirmPassword(confirmPassword);
	}
	


	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() {
		return "Password: "+ getPassword() + " and confirmPassword: "+getConfirmPassword() + " are not the same!";
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getConfirmPassword() {
		return confirmPassword;
	}



	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
