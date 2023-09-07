package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.wibmo.constants.SQLQueriesConstant;

import com.wibmo.entity.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course,String>{
	/**
	 * Assigns course to instructor
	 * @param courseId
	 * @param instructorId
	 */
	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.ASSIGN_COURSE_QUERY)
	void assignCourse(@Param(value="courseId") String courseId, @Param(value="instructorId") String instructorId);

	/**
	 * decrements available seats in a course by 1
	 * @param courseId
	 */
	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.DECREMENT_COURSE_SEATS)
	void decrementSeats(@Param(value="courseId") String courseId);
	
	/**
	 * Increments available sestas for a course by 1
	 * @param courseId
	 */
	@Modifying
	@Transactional
	@Query(SQLQueriesConstant.INCREMENT_SEAT_QUERY)
	void incrementSeats(@Param(value="courseId") String courseId);
	
	/**
	 * checks if seat is available for a course
	 * @param courseId
	 * @return
	 */
	@Query(SQLQueriesConstant.SEAT_AVAILABLE)
	boolean existsSeatsByCourseId(@Param("courseId") String courseId);
	
	/**
	 * finds professor
	 * @param profId
	 * @return
	 */
	Iterable<Course> findByInstructorId(String profId);
	
	/**
	 * calculate Total fee for student
	 * @param studentId
	 * @param sem
	 * @return total fee
	 */
	@Query(SQLQueriesConstant.TOTAL_FEES)
	double calculateFeeForStudentWithSem(String studentId,int sem);

	/**
	 * find course using courseId
	 * @param courseCode
	 * @return
	 */
	Optional<Course> findByCourseId(String courseCode);
}
