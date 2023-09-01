/**
 * 
 */
package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;

import com.wibmo.entity.Payment;

/**
 * 
 */
public interface PaymentRepository extends CrudRepository<Payment, String>{
	
}
