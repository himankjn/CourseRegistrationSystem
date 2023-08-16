package com.wibmo.dao;

import java.sql.*;
import com.wibmo.constants.*;
import com.wibmo.utils.*;

/**
 * @author Bhuvan
 */

/**
 * UserDAOInterface implementation 
 */
public class UserDAOImpl implements UserDAOInterface{
	PreparedStatement stmt = null;
	private static final Connection conn = DBUtils.getConnection();
	private static volatile UserDAOImpl instance = null;
	
	/**
	 * Default constructor
	 */
	private UserDAOImpl() {
		
	}
	/**
	 * Method to make UserDAOInterfaceImpl Singleton
	 * @return
	 */
	public static UserDAOImpl getInstance() {
		if(instance==null)
		{
			synchronized(UserDAOImpl.class){
				instance=new UserDAOImpl();
			}
		}
		return instance;
	}
	
	/**
	 * method to verify credentials
	 * @param userId, password to verify user
	 * @return boolean
	 */
	@Override
	public boolean verifyCredentials(String userId, String password) {
		int status = 1;
		try {
			stmt = conn.prepareStatement(SQLQueriesConstant.GET_USER);
			stmt.setString(1, userId);
			stmt.setString(2, password);
			status = stmt.executeUpdate();
		}catch(SQLException se)
		{
			se.printStackTrace();
		}
		return status>0;
	}
	
	/**
	 * method to update password of user with Id userId
	 * @param userId, newPassword 
	 * @return update Status
	 */
	@Override
	public boolean updatePassword(String userId, String newPassword) {
		int status = 1;
		try {
			stmt = conn.prepareStatement(SQLQueriesConstant.UPDATE_USER);
			stmt.setString(1, newPassword);
			stmt.setString(2, userId);
			status = stmt.executeUpdate();
		}catch(SQLException se)
		{
			se.printStackTrace();
		}
		return status>0;
	}
	
	/**
	 * method to get role of user
	 * @param userId 
	 * @return Role of user
	 */
	@Override
	public String getRole(String userId) {
		String role = null;
		try {
			stmt = conn.prepareStatement(SQLQueriesConstant.GET_ROLE);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery(SQLQueriesConstant.GET_ROLE);
			if(rs.next())
			{
				role = rs.getString("role");
			}
			
		}catch(SQLException se)
		{
			se.printStackTrace();
		}
		return role;
	}

}
