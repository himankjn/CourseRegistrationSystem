package com.wibmo.service.Impl;

import com.wibmo.entity.User;
import com.wibmo.exception.RoleMismatchException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.UserRepository;
import com.wibmo.service.UserServiceInterface;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Bhuvan
 * Implementations of User Operations
 *
 */

@Service
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	UserRepository userRepository;
	/**
	 * Method to update password of a user
	 * @param userID
	 * @param newPassword
	 * @return boolean indicating if the password is updated successfully
	 * @throws UserNotFoundException 
	 */
	@Override
	public boolean updatePassword(String userID,String newPassword) throws UserNotFoundException {
		Optional<User> user=userRepository.findByUserId(userID);
		if(user.isEmpty()) {
			throw new UserNotFoundException(userID);
		}
		else {
			userRepository.updatePassword(userID, newPassword);
			return true;
		}
	}

//	
	/**
	 * Method to verify User credentials
	 * @param userID
	 * @param password
	 * @return boolean indicating if user exists in the database
	 */

	@Override
	public boolean verifyCredentials(String userID, String password) throws UserNotFoundException {
		Optional<User> user=userRepository.findByUserId(userID);
		if(user.isEmpty()) {
			throw new UserNotFoundException(userID);
		}
		else {
			return password.equals(user.get().getPassword());
		}
	}
	
	/**
	 * Method to get role of a specific User
	 * @param userId
	 * @return RoleConstant of the User
	 */

	@Override
	public void verifyUserRole(String userId, String role) throws RoleMismatchException, UserNotFoundException {
		String actualRole = getRole(userId);
		if(!actualRole.equals(role))
			throw new RoleMismatchException(userId,role);
	}
	
	@Override
	public String getRole(String userId) throws UserNotFoundException {
		Optional<User> user=userRepository.findByUserId(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException(userId);
		}
		else {
			return user.get().getRole().toString();
		}
	}

	


	

}