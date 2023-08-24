/**
 * 
 */
package com.wibmo.business;

import java.util.UUID;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.NotificationDAOInterface;
import com.wibmo.dao.NotificationDAOImpl;

/**
 * @author himank
 *
 */
public class NotificationServiceImpl implements NotificationServiceInterface{
	
	private static volatile NotificationServiceImpl instance=null;
	NotificationDAOInterface notificationDaoInterface=NotificationDAOImpl.getInstance();
	private NotificationServiceImpl() {}
	
	/**
	 * Method to make NotificationDaoOperation Singleton
	 * @return
	 */
	public static NotificationServiceImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(NotificationServiceImpl.class){
				instance=new NotificationServiceImpl();
			}
		}
		return instance;
	}
	
	/**
	 * Method to send notification
	 * @param type: type of the notification to be sent
	 * @param studentId: student to be notified
	 * @param modeOfPayment: payment mode used
	 * @return notification id for the record added in the database
	 */
	@Override
	public int sendNotification(NotificationTypeConstant type, String studentId,PaymentModeConstant modeOfPayment,double amount) {
		int notificationId = -1; 
		try {
			notificationId = notificationDaoInterface.sendNotification(type, studentId, modeOfPayment, amount);
		}
		catch(Exception ex){
			System.out.println(ex.getStackTrace());
		}
		return notificationId;
	}

		/**
	 * Method to return UUID for a transaction
	 * @param notificationId: notification id added in the database
	 * @return transaction id of the payment
	 */
	@Override
	public String getReferenceId(int notificationId) {
		String referenceId = "";
		try {
			referenceId = notificationDaoInterface.getReferenceId(notificationId);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return referenceId;
	}


	
	
	
}