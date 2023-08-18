/**
 * 
 */
package com.wibmo.validator;

import java.util.List;

import com.wibmo.exception.UserNotFoundException;

import com.wibmo.bean.*;

/**
 * @author nikita
 *
 */
public class AdminValidator {
	

	/**
	 * Method to validate if newCourse is not already present in catalog
	 * @param newCourse
	 * @param courseList
	 * @return if newCourse is not already present in catalog
	 */
	public static boolean isValidNewCourse(Course newCourse, List<Course> courseList) {
		for(Course course : courseList) {
			if(newCourse.getCourseId().equalsIgnoreCase(course.getCourseId())) {
				return false; 
			}
		}
		return true;
	}
	
	/**
	 * Method to validate if dropCourse is already present in catalog
	 * @param dropCourseCode
	 * @param courseList
	 * @return if dropCourse is already present in catalog
	 */
	public static boolean isValidDropCourse(String dropCourseCode, List<Course> courseList) {
		for(Course course : courseList) {
			if(dropCourseCode.equalsIgnoreCase(course.getCourseId())) {
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * Method to validate if studentId is still unapproved
	 * @param studentId
	 * @param studentList
	 * @return if studentId is still unapproved
	 */
	public static boolean isValidUnapprovedStudent(String studentId, List<Student> studentList) {
		for(Student student : studentList) {
			if(studentId == student.getStudentId()) {
				return true;
			}
		}
		return false;
	}
	
	public static void verifyValidProfessor(String professorId,List<Professor> professors) throws UserNotFoundException {
		for(Professor prof: professors) {
			if(prof.getUserId().equals(professorId)) {
				return;
			}
		}
		throw new UserNotFoundException(professorId);
	}
}
