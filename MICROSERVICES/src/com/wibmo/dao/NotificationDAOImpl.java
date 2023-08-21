/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;


import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.constants.SQLQueriesConstant;
import com.wibmo.utils.DBUtils;

/**
 * @author himank
 *
 */
public class NotificationDAOImpl implements NotificationDAOInterface{

	
	private static volatile NotificationDAOImpl instance=null;

	/**
	 * Default Constructor
	 */
	private NotificationDAOImpl()
	{

	}
	
	/**
	 * Method to make NotificationDaoOperation Singleton
	 * @return
	 */
	public static NotificationDAOImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(NotificationDAOImpl.class){
				instance=new NotificationDAOImpl();
			}
		}
		return instance;
	}
	
	/**
	 * Send Notification using SQL commands
	 * @param type: type of the notification to be sent
	 * @param studentId: student to be notified
	 * @param modeOfPayment: mode of payment used, defined in enum
	 * @param amount
	 * @return notification id for the record added in the database
	 * @throws SQLException
	 */
	public int sendNotification(NotificationTypeConstant type, String studentId,PaymentModeConstant modeOfPayment,double amount) throws SQLException{
		int notificationId=0;
		Connection connection=DBUtils.getConnection();
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQLQueriesConstant.INSERT_NOTIFICATION,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, studentId);
			ps.setString(2,type.toString());
			if(type==NotificationTypeConstant.PAYED)
			{
				//insert into payment, get reference id and add here
				String referenceId;
				
				try {
					referenceId = addPayment(studentId, modeOfPayment,amount);
				}catch(SQLException ex) {
					ex.printStackTrace();
					throw ex;
				}
				ps.setString(3, referenceId);	
			}
			else
				ps.setString(3,"");
				
			ps.executeUpdate();
			ResultSet results=ps.getGeneratedKeys();
			if(results.next())
				notificationId=results.getInt(1);
			
			switch(type)
			{
			case REGISTRATION:
				System.out.println("Registration successfull. Administration will verify the details and approve it!");
				break;
			case APPROVED:
				System.out.println("Student with id "+studentId+" has been approved!");
				break;
			case PAYED:
				System.out.println("Student with id "+studentId+" fee has been paid");
			}
			
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		return notificationId;
	}

	/**
	 * Perform Payment actions using SQL commands
	 * @param studentId: Id of the student for which the payment is done
	 * @param modeOfPayment: mode of payment used, defined in enum
	 * @param amount 
	 * @return: reference id of the transaction
	 * @throws SQLException
	 */
	public String addPayment(String studentId, PaymentModeConstant modeOfPayment,double amount) throws SQLException
	{
		String referenceId = "";
		Connection connection=DBUtils.getConnection();
		try
		{
			referenceId=UUID.randomUUID().toString();
			//INSERT_NOTIFICATION = "insert into notification(studentId,type,referenceId) values(?,?,?);";
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.INSERT_PAYMENT);
			statement.setString(1, studentId);
			statement.setString(2, modeOfPayment.toString());
			statement.setString(3,referenceId.toString());
			statement.setDouble(4, amount);
			statement.executeUpdate();
			//check if record is added
			int status = statement.executeUpdate();
			
			if(status==0) {
				throw new SQLException("Insert not happened into the payment table");
			}
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		return referenceId;

	}


	public String getReferenceId(int notificationId) throws SQLException {
		String referenceId = "";
		Connection connection=DBUtils.getConnection();
		try
		{
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_PAYMENT_UUID);
			statement.setInt(1, notificationId);
			ResultSet rs = statement.executeQuery();
			
			referenceId = rs.getString(1);
			
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		
		return referenceId;
	}

}
