package com.wibmo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.entity.Course;
import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.RegisteredCourseId;

public interface RegisteredCourseRepository extends CrudRepository<RegisteredCourse,RegisteredCourseId>{

	Iterable<RegisteredCourse> findByStudentId(String studentId);
	
	
	@Query("select count(rc) from RegisteredCourse rc where rc.studentId=:studentId")
	int numberOfRegisteredCourses(@Param(value="studentId") String studentId);
	
	@Query("SELECT CASE WHEN COUNT(rc) > 0 THEN true ELSE false END FROM RegisteredCourse rc WHERE rc.courseId = :courseId AND rc.studentId = :studentId")
    boolean existsByCourseIdAndStudentId(@Param("courseId") String courseId, @Param("studentId") String studentId);
	
	@Query(value = "SELECT c.courseId,c.seats,c.courseName,c.professorId FROM Course c INNER JOIN RegisteredCourse rc ON c.courseId = rc.courseId WHERE rc.studentId = :studentId", nativeQuery = true)
	List<Object[]> enrolledCoursesByStudentId(@Param("studentId") String studentId);

	@Query(value = "select * from Course where courseId not in  (select courseId  from registeredcourse where studentId = ?) and seats > 0", nativeQuery = true)
	List<Object[]> availableCoursesByStudentId(@Param("studentId") String studentId);
	
}
