/**
 * 
 */
package com.wibmo.dao;

/**
 * @author Shanmukh
 *
 */
public interface NotificationDaoInterface {


		/**
		 * Send Notification using SQL commands
		 * @param type: type of the notification to be sent
		 * @param studentId: student to be notified
		 * @param modeOfPayment: mode of payment used, defined in enum
		 * @param amount
		 * @return notification id for the record added in the database
		 * @throws SQLException
		 */
		public int sendNotification(String type,int studentId,String modeOfPayment,double amount);
		
}

