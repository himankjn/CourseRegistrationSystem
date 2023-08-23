package com.wibmo.business;

import com.wibmo.dao.UserDAOImpl;
import com.wibmo.dao.UserDAOInterface;
import com.wibmo.exception.RoleMismatchException;
import com.wibmo.exception.UserNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.business.UserServiceInterface;

/**
 * 
 * @author Bhuvan
 * Implementations of User Operations
 *
 */

@Service
public class UserServiceImpl implements UserServiceInterface {
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserDAOInterface userDaoInterface;
	
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
			logger.error("User with given user id: "+ userID+" not found!");
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
	public void verifyUserRole(String userId, String role) throws RoleMismatchException {
		String actualRole=getRole(userId);
		if(!actualRole.equals(role))
			throw new RoleMismatchException(userId,role);
	}

	


	

}