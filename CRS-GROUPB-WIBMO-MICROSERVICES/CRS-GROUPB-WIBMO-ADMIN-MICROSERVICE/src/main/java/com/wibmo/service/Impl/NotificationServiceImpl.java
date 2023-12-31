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
	public int sendStudentApprovalNotification(NotificationTypeConstant type, String studentId) {
	
		int notificationId = 0;
		String notificationTopicName = "approvalTopic";
		Notification newNotification = new Notification();
		newNotification.setReferenceId("-");
		newNotification.setType(type.toString());
		newNotification.setUserId(studentId);
		kafkaNotificationTemplate.send(notificationTopicName,newNotification);
//		notificationId = notificationRepository.save(newNotification).getNotifId();
		return notificationId;
	}
	
	


	@Override
	@KafkaListener(topics = "approvalTopic")
	public void listenApprovalNotification(Notification notification) {
	    {
	    	notificationRepository.save(notification);
	    	//save to notificaiton db instead of consuming  and then read from db for student specific notfication
	        System.out.println(notification.getUserId()+" is Approved");
	    }

	}
	
	
	
}