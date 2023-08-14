/**
 * 
 */
package com.wibmo.constants;

/**
 * @author Himank
 *
 */
public class SQLQueriesConstant {
		public static final String GET_COURSES="select * from course where professorId=?";
		public static final String GET_ENROLLED_STUDENTS = "select * from student inner join registeredcourse on student.studentId=registeredcourse.studentId where registeredcourse.courseid=?";
		
		public static final String GET_COURSE_CATALOUGE = "select * from course";
		public static final String GET_PROFESSORS = "select * from professor";
		public static final String GET_PENDING_ADMINSSIONED_STUDENTS = "select * from student WHERE student.feePaid=false";
		public static final String GET_GRADES = "select credits,grade from registeredcourse inner join course on course.courseId=registeredcourse.courseId where studentId=?";
		public static final String ADD_PROFESSOR = "insert into professor values(?,?)";
		public static final String ADD_COURSE = "insert into course(`courseId`,`credits`,`courseName`) values(?,?,?,?)";
		public static final String REMOVE_COURSE = "DELETE  FROM course WHERE course.courseId=?";
		public static final String assignCourse = "UPDATE course SET course.professorId=? WHERE course.courseId=?";
}
