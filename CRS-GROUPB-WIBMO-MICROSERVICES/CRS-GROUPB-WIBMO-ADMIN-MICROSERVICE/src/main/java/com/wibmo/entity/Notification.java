package com.wibmo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "notification")
public class Notification implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notificationId")
	private int notifId;
	@Column(name = "studentId")
	private String userId;
	@Column(name = "type")
	private String type;
	@Column(name="referenceId")
	private String referenceId;
	/**
	 * @return the notifId
	 */
	public int getNotifId() {
		return notifId;
	}
	/**
	 * @param notifId the notifId to set
	 */
	public void setNotifId(int notifId) {
		this.notifId = notifId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the message
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param message the message to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	public String getReferenceId(){
		return this.referenceId;
	}

	public void setReferenceId(String referenceId){
		this.referenceId = referenceId;
	}
}
