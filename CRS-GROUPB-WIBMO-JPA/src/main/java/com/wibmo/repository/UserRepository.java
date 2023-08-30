package com.wibmo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.entity.User;
import com.wibmo.constants.SQLQueriesConstant;

public interface UserRepository extends CrudRepository<User,String>{
	Optional<User> findByUserId(String userId);

	@Modifying
	@Transactional
	@Query(value=SQLQueriesConstant.UPDATE_PASSWORD,nativeQuery = true)
	void updatePassword(String userId, String newPassword);
}
