package com.wibmo.repository;

import java.util.Optional;
import com.wibmo.constants.SQLQueriesConstant;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.entity.Student;


public interface StudentRepository extends CrudRepository<Student,String>{
	Iterable<Student> findAllByIsApproved(boolean isApproved);
	Optional<Student> findByUserId(String userId);
	Optional<Student>findByStudentId(String studentId);
	
	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.SET_IS_APPROVED_BY_ID)
	void setIsApprovedById(@Param(value = "approved") boolean isApproved, @Param(value = "studentId") String studentId);
	
	@Modifying
	@Transactional
	@Query(value=SQLQueriesConstant.SET_ALL_IS_APPROVED, nativeQuery = true)
	void setAllIsApproved(@Param(value = "isApproved") boolean isApproved);

	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.SET_GENERATED_REPORT_CARD_TRUE)
	void setGeneratedReportCardTrue(@Param(value="studentId")String studentId);

	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.SET_STUDENT_SEMESTER)
	void setSem(@Param(value="studentId")String studentId,@Param(value="sem") int sem);

	
}
