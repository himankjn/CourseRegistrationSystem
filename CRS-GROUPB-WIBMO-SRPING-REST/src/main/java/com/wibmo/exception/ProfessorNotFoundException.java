/**
 * 
 */
package com.wibmo.exception;

/**
 * @author himank
 */
public class ProfessorNotFoundException extends Exception{
	private String professorId;
	
	public ProfessorNotFoundException(String professorId)
	{	
		this.professorId = professorId;
	}

	/**
	 * Getter function for professor code
	 * @return
	 */
	public String getprofessorId()
	{
		return professorId;
	}
	

	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() 
	{
		return "professor with professorId: " + professorId + " not found.";
	}
}
