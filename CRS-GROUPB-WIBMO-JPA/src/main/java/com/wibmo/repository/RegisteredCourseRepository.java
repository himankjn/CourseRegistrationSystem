package com.wibmo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.constants.GradeConstant;
import com.wibmo.entity.EnrolledStudent;
import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.RegisteredCourseId;

public interface RegisteredCourseRepository extends CrudRepository<RegisteredCourse,RegisteredCourseId>{

	Iterable<RegisteredCourse> findByRegisteredCourseIdStudentId(String studentId);

	Iterable<RegisteredCourse> findByGrade(String grade);
	
	
	@Modifying
	@Transactional
	@Query("update RegisteredCourse r set r.grade=:grade where r.registeredCourseId.studentId=:studentId and r.registeredCourseId.courseId=:courseId")
	void addGrade( @Param(value="studentId")String studentId,@Param(value="courseId")String courseId,@Param(value="grade")GradeConstant grade);
	
	
	@Modifying
	@Transactional
	@Query("select new com.wibmo.entity.EnrolledStudent(c.courseId ,c.courseName, r.registeredCourseId.studentId) from Course c inner join RegisteredCourse r on c.courseId = r.registeredCourseId.courseId where c.courseId=:courseId")
	List<EnrolledStudent> getEnrolledStudents(@Param(value="courseId") String courseId);
	

}
