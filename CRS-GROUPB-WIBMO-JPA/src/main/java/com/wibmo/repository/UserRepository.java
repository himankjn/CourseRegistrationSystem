package com.wibmo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.entity.User;

public interface UserRepository extends CrudRepository<User,String>{
	Optional<User> findByUserId(String userId);

	@Modifying
	@Transactional
	@Query(value="update user set password=?2 where userId = ?1",nativeQuery = true)
	void updatePassword(String userId, String newPassword);
}
