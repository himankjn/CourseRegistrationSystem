package com.wibmo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.entity.Student;


public interface StudentRepository extends CrudRepository<Student,String>{
	Iterable<Student> findAllByIsApproved(boolean isApproved);

	@Modifying
	@Transactional
	@Query("update Student s set s.isApproved = :approved where s.studentId = :studentId")
	void setIsApprovedById(@Param(value = "approved") boolean isApproved, @Param(value = "studentId") String studentId);
	
	@Modifying
	@Transactional
	@Query("update Student s set s.isApproved = :isApproved")
	void setAllIsApproved(@Param(value = "isApproved") boolean isApproved);

	@Modifying
	@Transactional
	@Query("update Student s set s.isReportGenerated = 1 where s.studentId = :studentId")
	void setGeneratedReportCardTrue(@Param(value="studentId")String studentId);
	
}
