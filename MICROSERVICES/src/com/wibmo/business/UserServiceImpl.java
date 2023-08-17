package com.wibmo.business;

import com.wibmo.dao.UserDAOImpl;
import com.wibmo.dao.UserDAOInterface;
import com.wibmo.exception.RoleMismatchException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.business.UserServiceInterface;

/**
 * 
 * @author Bhuvan
 * Implementations of User Operations
 *
 */
public class UserServiceImpl implements UserServiceInterface {
	
	private static volatile UserServiceImpl instance=null;
	UserDAOInterface userDaoInterface= UserDAOImpl.getInstance();
	private UserServiceImpl()
	{
		
	}
	
	/**
	 * Method to make UserOperation Singleton
	 * @return
	 */
	public static UserServiceImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(UserServiceImpl.class){
				instance=new UserServiceImpl();
			}
		}
		return instance;
	}

	/**
	 * Method to update password of a user
	 * @param userID
	 * @param newPassword
	 * @return boolean indicating if the password is updated successfully
	 */
	
	@Override
	public boolean updatePassword(String userID,String newPassword) {
		return userDaoInterface.updatePassword(userID, newPassword);
	}

	
	/**
	 * Method to verify User credentials
	 * @param userID
	 * @param password
	 * @return boolean indicating if user exists in the database
	 */

	@Override
	public boolean verifyCredentials(String userID, String password) throws UserNotFoundException {
		try {
			return userDaoInterface.verifyCredentials(userID, password);
		}
		catch(UserNotFoundException e){
			System.out.println("User with given user id: "+ userID+" not found!");
			return false;
		}
		
		
	}
	
	/**
	 * Method to get role of a specific User
	 * @param userId
	 * @return RoleConstant of the User
	 */
	@Override
	public String getRole(String userId) {
		return userDaoInterface.getRole(userId);
	}

	@Override
	public void verifyUserRole(String userId, int roleNum) throws RoleMismatchException {
		String role="";
		switch(roleNum) {
			case 1: role="ADMIN";
			break;
			case 2: role="PROFESSOR";
			break;
			case 3: role="STUDENT";
			break;
		}
		String actualRole=getRole(userId);
		if(!actualRole.equals(role))
			throw new RoleMismatchException(userId,role);
	}

	


	

}