/**
 *
 */
package com.wibmo.service;


import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.entity.Notification;
import com.wibmo.entity.Payment;


/**
 * @author himank
 *
 */

@Service
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
	int sendPaymentNotification(NotificationTypeConstant type, String studentId, PaymentModeConstant modeOfPayment, double amount);

	void listenPaymentNotification(Payment payment);

	int sendStudentRegistrationNotification(NotificationTypeConstant type, String studentId);

	void listenStudentRegistrationNotification(Notification notification);

	
}