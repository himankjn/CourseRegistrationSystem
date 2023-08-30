package com.wibmo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wibmo.constants.GradeConstant;
import com.wibmo.entity.EnrolledStudent;
import com.wibmo.entity.RegisteredCourse;
import com.wibmo.entity.RegisteredCourseId;
import com.wibmo.constants.SQLQueriesConstant;

public interface RegisteredCourseRepository extends CrudRepository<RegisteredCourse,RegisteredCourseId>{

	Iterable<RegisteredCourse> findByRegisteredCourseIdStudentIdAndSem(String studentId,int sem);

	Iterable<RegisteredCourse> findByGrade(String grade);
	
	@Query(SQLQueriesConstant.NUMBER_OF_REGISTERED_COURSES)
	int numberOfRegisteredCourses(@Param(value="studentId") String studentId);
	
	@Query(SQLQueriesConstant.EXIST_BY_STUDENT_ID_COURSE_ID)
    boolean existsByCourseIdAndStudentId(@Param("courseId") String courseId, @Param("studentId") String studentId);
	
	@Query(value = SQLQueriesConstant.ENROLLED_COURSES_BY_STUDENT_ID, nativeQuery = true)
	List<Object[]> enrolledCoursesByStudentIdAndSem(@Param("studentId") String studentId, @Param("sem") int sem);

	@Query(value = SQLQueriesConstant.AVAILABLE_COURSE_BY_STUDENT_ID, nativeQuery = true)
	List<Object[]> availableCoursesByStudentIdAndSem(@Param("studentId") String studentId, @Param("sem")int sem);
	
	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.ADD_GRADE)
	void addGrade( @Param(value="studentId")String studentId,@Param(value="courseId")String courseId,@Param(value="grade")GradeConstant grade);
	
	
	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.ENROLLED_STUDENT)
	List<EnrolledStudent> getEnrolledStudents(@Param(value="courseId") String courseId);
	

}
