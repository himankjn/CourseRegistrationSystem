/**
 * 
 */
package com.wibmo.dao;

/**
 * @author Shanmukh
 *
 */
public interface NotificationDaoInterface {

	public int sendNotification(NotificationType type,int studentId,PaymentMode modeofPay,double amount);
}
