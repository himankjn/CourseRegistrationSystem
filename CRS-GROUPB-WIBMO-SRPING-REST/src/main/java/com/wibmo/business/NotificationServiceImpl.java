/**
 * 
 */
package com.wibmo.business;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.dao.NotificationDAOInterface;
import com.wibmo.dao.NotificationDAOImpl;

/**
 * @author himank
 *
 */

@Service
public class NotificationServiceImpl implements NotificationServiceInterface{

	@Autowired
	private NotificationDAOInterface notificationDaoInterface;

	
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