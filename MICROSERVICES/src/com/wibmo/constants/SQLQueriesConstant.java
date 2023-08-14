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
		public static final String ADD_GRADE="update registeredcourse set grade=? where courseId=? and studentId=?";
}
