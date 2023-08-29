package com.wibmo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.entity.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course,String>{
	@Modifying
	@Transactional
	@Query("update Course set instructorId = instructorId where courseId = courseId")
	void assignCourse(@Param(value="courseId") String courseId, @Param(value="instructorId") String instructorId);
	
	Iterable<Course> findByInstructorId(String profId);
}
