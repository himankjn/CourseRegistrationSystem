package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;

import com.wibmo.entity.ProfessorCourseRequest;
import com.wibmo.entity.ProfessorCourseRequestId;

public interface ProfessorCourseRequestRepository extends CrudRepository<ProfessorCourseRequest,ProfessorCourseRequestId>{

	Iterable<ProfessorCourseRequest> findByCourseId(String courseId);

}
