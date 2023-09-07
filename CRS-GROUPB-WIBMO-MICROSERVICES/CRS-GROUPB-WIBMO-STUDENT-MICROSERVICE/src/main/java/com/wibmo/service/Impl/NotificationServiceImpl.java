/**
 * 
 */
package com.wibmo.service.Impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
	
	@Autowired
	private KafkaTemplate<String,Payment> kafkaPaymentTemplate;

	@Autowired
	private KafkaTemplate<String, Notification> kafkaNotificationTemplate;
	
	
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
	public String sendPaymentNotification(NotificationTypeConstant type, String studentId, PaymentModeConstant modeOfPayment,double amount) {
	
		String paymentTopicName = "paymentTopic";
		Payment newPayment = new Payment();
		String referenceId = UUID.randomUUID().toString();
		newPayment.setAmount(amount);
		newPayment.setInvoiceId(referenceId);
		newPayment.setPaymentMode(modeOfPayment.toString());
		newPayment.setStatus(true);
		newPayment.setStudentId(studentId);
		kafkaPaymentTemplate.send(paymentTopicName,newPayment);
//		paymentRepository.save(newPayment);
		return referenceId;
	}

	@Override
	public int sendStudentRegistrationNotification(NotificationTypeConstant type, String studentId) {
	
		String notificationTopicName = "registrationTopic";;
		Notification newNotification = new Notification();
		newNotification.setReferenceId("-");
		newNotification.setType(type.toString());
		newNotification.setUserId(studentId);
		kafkaNotificationTemplate.send(notificationTopicName,newNotification);
//		int notificationId = notificationRepository.save(newNotification).getNotifId();
		return -1;
	}

	@Override
	@KafkaListener(topics = "registrationTopic")
	public void listenStudentRegistrationNotification(Notification notification) {
	    {
	    	notificationRepository.save(notification);
	        System.out.println(notification.getUserId()+"is registered");
	    }
	}
	
	@Override
	@KafkaListener(topics = "paymentTopic")
	public void listenPaymentNotification(Payment payment) {
	    {
	    	paymentRepository.save(payment);
	        System.out.println(payment.getStudentId()+" has paid: "+payment.getAmount());
	    }
	}
	
	
}