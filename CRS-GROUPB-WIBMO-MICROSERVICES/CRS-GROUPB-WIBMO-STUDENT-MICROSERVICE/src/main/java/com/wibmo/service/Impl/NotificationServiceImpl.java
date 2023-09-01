/**
 * 
 */
package com.wibmo.service.Impl;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.constants.PaymentModeConstant;
import com.wibmo.entity.Notification;
import com.wibmo.entity.Payment;
import com.wibmo.repository.NotificationRepository;
import com.wibmo.repository.PaymentRepository;
import com.wibmo.service.NotificationServiceInterface;

/**
 * @author himank
 *
 */

@Service
public class NotificationServiceImpl implements NotificationServiceInterface{

	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public String getReferenceId(int notificationId) {
		String referenceId = "";
		try {
			referenceId = notificationRepository.findById(notificationId).get().getReferenceId();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return referenceId;
	}

	@Override
	public int sendNotification(NotificationTypeConstant type, String studentId, PaymentModeConstant modeOfPayment,double amount) {
	
		int notificationId;
		if(type==NotificationTypeConstant.PAYED)
		{
			//insert into payment, get reference id and add here
			String referenceId;
			
			Payment newPayment = new Payment();
			referenceId = UUID.randomUUID().toString();
			newPayment.setAmount(amount);
			newPayment.setInvoiceId(referenceId);
			newPayment.setPaymentMode(modeOfPayment.toString());
			newPayment.setStatus(true);
			newPayment.setStudentId(studentId);
			paymentRepository.save(newPayment);
		
			
			Notification newNotification = new Notification();
			newNotification.setReferenceId(referenceId);
			newNotification.setUserId(studentId);
			newNotification.setType(type.toString());
			notificationId = notificationRepository.save(newNotification).getNotifId();
		}
		else {
			Notification newNotification = new Notification();
			newNotification.setReferenceId("-");
			newNotification.setType(type.toString());
			newNotification.setUserId(studentId);
			notificationId = notificationRepository.save(newNotification).getNotifId();
		}
			
		
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
		return notificationId;
	}


	
	
	
}