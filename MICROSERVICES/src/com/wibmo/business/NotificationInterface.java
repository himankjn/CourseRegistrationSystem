package com.wibmo.business;

/**
 * @author Himank
 *
 */
public interface NotificationInterface {

    /**
     * Method to send payment notification to student.
     * @param type
     * @param studentId
     * @param amount
     * @return
     */
    public int sendPaymentNotification(String type,String studentId,double amount);



}