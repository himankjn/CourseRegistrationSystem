package com.wibmo.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "payment")
public class Payment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "studentId")
	private String studentId;
	@Id
	@Column(name = "referenceId")
	private String invoiceId;
	@Column(name = "amount")
	private double amount;
	@Column(name = "modeOfPayment")
	private String paymentMode;
	@Transient
	private boolean status;

	/**
	 * @return the payment mode of transaction
	 */
	public String getPaymentMode(){
		return paymentMode;
	}

	/**
	 * sets the payment mode of a transaction
	 * @param paymentMode
	 */
	public void setPaymentMode(String paymentMode){
		this.paymentMode = paymentMode;
	}


	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
}
