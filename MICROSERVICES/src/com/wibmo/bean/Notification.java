package com.wibmo.bean;

public class Notification {
	private int notifId;
	private int userId;
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
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
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
