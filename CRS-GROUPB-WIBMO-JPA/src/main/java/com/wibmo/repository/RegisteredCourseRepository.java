package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;

import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.RegisteredCourseId;

public interface RegisteredCourseRepository extends CrudRepository<RegisteredCourse,RegisteredCourseId>{

	Iterable<RegisteredCourse> findByStudentId(String studentId);
	
}
