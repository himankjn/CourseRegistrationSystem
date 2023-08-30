package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	@Query("update Course set instructorId = :instructorId where courseId = :courseId")
	void assignCourse(@Param(value="courseId") String courseId, @Param(value="instructorId") String instructorId);

	/**
	 * decrements available seats in a course by 1
	 * @param courseId
	 */
	@Modifying
	@Transactional
	@Query("update Course set seats = seats-1 where courseId = :courseId")
	void decrementSeats(@Param(value="courseId") String courseId);
	
	/**
	 * Increments available sestas for a course by 1
	 * @param courseId
	 */
	@Modifying
	@Transactional
	@Query("update Course set seats = seats+1 where courseId = :courseId")
	void incrementSeats(@Param(value="courseId") String courseId);
	
	/**
	 * checks if seat is available for a course
	 * @param courseId
	 * @return
	 */
	@Query("SELECT CASE WHEN c.seats > 0 THEN true ELSE false END FROM Course c WHERE c.courseId = :courseId")
    boolean existsSeatsByCourseId(@Param("courseId") String courseId);
	
	/**
	 * finds professor
	 * @param profId
	 * @return
	 */
	Iterable<Course> findByInstructorId(String profId);
	
	@Query("select sum(c.courseFee) from Course c inner join RegisteredCourse r on c.courseId=r.registeredCourseId.courseId where r.registeredCourseId.studentId=:studentId and r.sem=:sem")
	double calculateFeeForStudentWithSem(String studentId,int sem);

	Optional<Course> findByCourseId(String courseCode);
}
