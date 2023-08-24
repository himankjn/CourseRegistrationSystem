package com.wibmo.exception;

/**
 * Exception to check if student is not registered 
 * @author nikita
 *
 */
public class StudentNotRegisteredException extends Exception{
	 private String message;
	 
	 public StudentNotRegisteredException(String message)
	 {
		 this.message=message;
	 }
	 
	 /**
	  * getter function for studentName
	  * @return
	  */
	 public String getMessage()
	 {
		 return message;
	 }
	 
	 
}