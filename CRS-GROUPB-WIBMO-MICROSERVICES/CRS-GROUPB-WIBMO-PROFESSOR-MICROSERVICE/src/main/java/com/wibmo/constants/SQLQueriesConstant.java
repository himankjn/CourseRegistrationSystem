/**
 * 
 */
package com.wibmo.constants;

/**
 * @author Bhuvan
 *
 */
public class SQLQueriesConstant {
	
		//AdminDao Queries
		public static final String DELETE_COURSE_QUERY = "DELETE FROM Course WHERE courseId = ?";
		public static final String ADD_COURSE_QUERY = "INSERT INTO Course(courseId, courseName, seats, professorId) values (?, ?, ?, ?)";
		public static final String VIEW_PENDING_ADMISSION_QUERY = "SELECT userId,name, password, role, gender, address, studentId FROM student, user WHERE isApproved = 0 and studentId = userId";
		public static final String APPROVE_STUDENT_QUERY = "UPDATE student SET isApproved = 1 WHERE studentId = ?";
		public static final String APPROVE_ALL_STUDENT_QUERY = "UPDATE student SET isApproved = 1 WHERE isApproved=0";
		public static final String ADD_USER_QUERY = "INSERT INTO User(userId, name, password, role, gender, address) values (?, ?, ?, ?, ?, ?)";
		public static final String DROP_USER_QUERY = "DELETE FROM User WHERE userId=?";
		public static final String ADD_PROFESSOR_QUERY = "INSERT INTO Professor(userId,professorId, department, designation) values (?, ?, ?, ?)";
		public static final String DROP_PROFESSOR_QUERY = "DELETE FROM professor WHERE userId=?";
		public static final String ASSIGN_COURSE_QUERY = "UPDATE Course SET instructorId = :instructorId WHERE courseId = :courseId";
		public static final String VIEW_COURSE_QUERY = "SELECT courseId, courseName, professorId FROM Course";
		public static final String VIEW_PROFESSOR_QUERY = "SELECT userId, name, gender, department, designation, address FROM Professor natural join User WHERE userId = professorId";
		public static final String SET_GENERATED_REPORT_CARD_TRUE = "UPDATE Student s SET s.isReportGenerated = 1 WHERE s.studentId = :studentId";
		public static final String GET_GENERATED_REPORT_CARD_TRUE = "SELECT isReportGenerated FROM student WHERE studentId = ?";
		public static final String ADD_STUDENT="INSERT INTO student (studentId,department,gradYear,isApproved,isRegistered,isReportGenerated,isPaid) values (?,?,?,0,0,0,0)";
		public static final String IS_APPROVED="SELECT isApproved FROM student WHERE studentId = ? ";
		public static final String GET_STUDENT_ID="SELECT studentId FROM student WHERE userId = ? ";
		public static final String SHOW_PROF_COURSE_REQUESTS = "SELECT userId FROM prof_course_request WHERE courseId=?";
		
		// StudentDao Queries
		//public static final String GET_COURSES_OF_STUDENT="SELECT * FROM";
		public static final String VIEW_REGISTERED_COURSES=" SELECT * FROM course INNER JOIN registeredcourse on course.courseId = registeredcourse.courseId WHERE registeredcourse.studentId = ?";
		public static final String VIEW_AVAILABLE_COURSES="SELECT * FROM course WHERE courseId not in  (SELECT courseId  FROM registeredcourse WHERE studentId = ?) and seats > 0";
		public static final String CHECK_COURSE_AVAILABILITY=" SELECT courseId FROM registeredcourse WHERE studentId = ? ";
		public static final String DECREMENT_COURSE_SEATS="UPDATE Course SET seats = seats-1 WHERE courseId = :courseId";
		public static final String ADD_COURSE="INSERT INTO registeredcourse (studentId,courseId,grade) values ( ? , ?, ?)";
		public static final String DROP_COURSE_QUERY = "DELETE FROM registeredcourse WHERE courseId = ? AND studentId = ?;";
		public static final String INCREMENT_SEAT_QUERY  = "UPDATE Course SET seats = seats+1 WHERE courseId = :courseId";
		public static final String CALCULATE_FEES  = "SELECT sum(courseFee) FROM course WHERE courseId in (SELECT courseId FROM registeredcourse WHERE studentId = ?);";
		public static final String VIEW_GRADE = "SELECT course.courseId,course.courseName,registeredcourse.grade FROM course INNER JOIN registeredcourse on course.courseId = registeredcourse.courseId WHERE registeredcourse.studentId = ?;";	
		public static final String GET_SEATS = "SELECT seats FROM course WHERE courseId = ?;";
		public static final String INSERT_PAYMENT = "INSERT INTO payment(studentId,modeofPayment,referenceId,amount) values(?,?,?,?);";
		public static final String INSERT_NOTIFICATION = "INSERT INTO notification(studentId,type,referenceId) values(?,?,?);";
		public static final String GET_NOTIFICATION = "SELECT * FROM notification WHERE referenceId = ?;";
		public static final String GET_REGISTRATION_STATUS=" SELECT isRegistered FROM student WHERE studentId = ? ";
		public static final String SET_REGISTRATION_STATUS="UPDATE student SET isRegistered = true  WHERE studentId=?";
		public static final String GET_PAYMENT_STATUS=" SELECT isPaid FROM student WHERE studentId = ? ";
		public static final String SET_PAYMENT_STATUS="UPDATE student SET isPaid = true  WHERE studentId=?";
		public static final String NUMBER_OF_REGISTERED_COURSES="SELECT count(rc) FROM RegisteredCourse rc WHERE rc.registeredCourseId.studentId=:studentId";
		public static final String IS_REGISTERED=" SELECT courseId FROM registeredcourse WHERE courseId=? and studentId=? ";
		public static final String GET_PAYMENT_UUID = "SELECT referenceId FROM notification WHERE notificationId=?";
		public static final String GET_GRADES = "SELECT grade FROM registeredcourse WHERE studentId=?";
		
		//ProfessorDao queries
		public static final String UPDATE_PASSWORD="UPDATE user SET password=?2 WHERE userId = ?1";
		public static final String VERIFY_CREDENTIALS="SELECT password FROM user WHERE userId = ?";
		public static final String GET_ROLE="SELECT role FROM user WHERE userId = ?";
		public static final String GET_COURSES="SELECT * FROM course WHERE professorId=?";
		public static final String GET_ENROLLED_STUDENTS="SELECT course.courseId,course.courseName,registeredcourse.studentId FROM course INNER JOIN registeredcourse on course.courseId = registeredcourse.courseId WHERE course.courseId=?";
		public static final String ADD_GRADE="UPDATE RegisteredCourse r SET r.grade=:grade WHERE r.registeredCourseId.studentId=:studentId and r.registeredCourseId.courseId=:courseId";
		public static final String GET_PROF_NAME = "SELECT name FROM user WHERE userId = ?";
		public static final String REQUEST_COURSE_ASSIGNMENT = "INSERT INTO prof_course_request(userId,courseId) values(?,?)";
		public static final String GET_UNASSIGNED_COURSES = "SELECT * FROM course WHERE professorId=?";
		public static final String GET_PROF_COURSE_REQUESTS = "SELECT userId FROM prof_course_request WHERE courseId=?";
		
		//Miscellaeous queries
		public static final String SEAT_AVAILABLE ="SELECT CASE WHEN c.seats > 0 THEN true ELSE false END FROM Course c WHERE c.courseId = :courseId";
		public static final String TOTAL_FEES=	"SELECT sum(c.courseFee) FROM Course c INNER JOIN RegisteredCourse r on c.courseId=r.registeredCourseId.courseId WHERE r.registeredCourseId.studentId=:studentId and r.sem=:sem";
		public static final String EXIST_BY_STUDENT_ID_COURSE_ID="SELECT CASE WHEN COUNT(rc) > 0 THEN true ELSE false END FROM RegisteredCourse rc WHERE rc.registeredCourseId.courseId = :courseId AND rc.registeredCourseId.studentId = :studentId";
		public static final String ENROLLED_COURSES_BY_STUDENT_ID="SELECT c.courseId,c.seats,c.courseName,c.professorId,c.courseFee,c.sem FROM Course c INNER JOIN RegisteredCourse rc ON c.courseId = rc.courseId WHERE rc.studentId = :studentId And c.sem =:sem";
		public static final String AVAILABLE_COURSE_BY_STUDENT_ID=	"SELECT * FROM Course WHERE courseId not in  (SELECT courseId  FROM registeredcourse WHERE studentId = :studentId) and seats > 0 and sem =:sem";
		public static final String ENROLLED_STUDENT	="SELECT new com.wibmo.entity.EnrolledStudent(c.courseId ,c.courseName, r.registeredCourseId.studentId) FROM Course c INNER JOIN RegisteredCourse r on c.courseId = r.registeredCourseId.courseId WHERE c.courseId=:courseId";
		public static final String SET_IS_APPROVED_BY_ID="UPDATE Student s SET s.isApproved = :approved WHERE s.studentId = :studentId";
		public static final String SET_ALL_IS_APPROVED="UPDATE Student s SET s.isApproved = :isApproved";
		public static final String SET_STUDENT_SEMESTER = "UPDATE Student s SET s.sem = :sem WHERE s.studentId = :studentId";
		
		
}
