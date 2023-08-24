/**
 *
 */
package com.wibmo.business;


import java.util.UUID;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;


/**
 * @author himank
 *
 */
public interface NotificationServiceInterface {
    
	/**
	 * Method to return UUID for a transaction
	 * @param notificationId: notification id added in the database
	 * @return transaction id of the payment
	 */
	String getReferenceId(int notificationId);

	/**
	 * Method to send notification
	 * @param type: type of the notification to be sent
	 * @param studentId: student to be notified
	 * @param modeOfPayment: payment mode used
	 * @return notification id for the record added in the database
	 */
	int sendNotification(NotificationTypeConstant type, String studentId, PaymentModeConstant modeOfPayment, double amount);


}