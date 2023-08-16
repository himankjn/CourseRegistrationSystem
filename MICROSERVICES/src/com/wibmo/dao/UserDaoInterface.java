/**
 * 
 */
package com.wibmo.dao;

/**
 * 
 */
public interface UserDAOInterface {
	
	public boolean verifyCredentials(String userId,String password);
	public boolean updatePassword(String userID,String newPassword);
	public String getRole(String userId);
}
