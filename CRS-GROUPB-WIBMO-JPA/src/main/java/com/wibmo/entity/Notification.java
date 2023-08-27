package com.wibmo.entity;

import java.io.Serializable;

public class Notification implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int notifId;
	private String userId;
	private String message;
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
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
