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
		public static final String DELETE_COURSE_QUERY = "delete from Course where courseId = ?";
		public static final String ADD_COURSE_QUERY = "insert into Course(courseId, courseName, seats, professorId) values (?, ?, ?, ?)";
		public static final String VIEW_PENDING_ADMISSION_QUERY = "select userId,name, password, role, gender, address, studentId from student, user where isApproved = 0 and studentId = userId";
		public static final String APPROVE_STUDENT_QUERY = "update student set isApproved = 1 where studentId = ?";
		public static final String APPROVE_ALL_STUDENT_QUERY = "update student set isApproved = 1 where isApproved=0";
		public static final String ADD_USER_QUERY = "insert into User(userId, name, password, role, gender, address) values (?, ?, ?, ?, ?, ?)";
		public static final String DROP_USER_QUERY = "DELETE FROM User where userId=?";
		public static final String ADD_PROFESSOR_QUERY = "insert into Professor(userId,professorId, department, designation) values (?, ?, ?, ?)";
		public static final String DROP_PROFESSOR_QUERY = "DELETE FROM professor where userId=?";
		public static final String ASSIGN_COURSE_QUERY = "update Course set professorId = ? where courseId = ?";
		public static final String VIEW_COURSE_QUERY = "select courseId, courseName, professorId from Course";
		public static final String VIEW_PROFESSOR_QUERY = "select userId, name, gender, department, designation, address from Professor natural join User where userId = professorId";
		public static final String SET_GENERATED_REPORT_CARD_TRUE = "update student set isReportGenerated = 1 where studentId = ?";
		public static final String GET_GENERATED_REPORT_CARD_TRUE = "select isReportGenerated from student where studentId = ?";
		public static final String ADD_STUDENT="insert into student (studentId,department,gradYear,isApproved,isRegistered,isReportGenerated,isPaid) values (?,?,?,0,0,0,0)";
		public static final String IS_APPROVED="select isApproved from student where studentId = ? ";
		public static final String GET_STUDENT_ID="select studentId from student where userId = ? ";
		public static final String SHOW_PROF_COURSE_REQUESTS = "select userId from prof_course_request where courseId=?";
		
		// StudentDao Queries
		//public static final String GET_COURSES_OF_STUDENT="select * from";
		public static final String VIEW_REGISTERED_COURSES=" select * from course inner join registeredcourse on course.courseId = registeredcourse.courseId where registeredcourse.studentId = ?";
		public static final String VIEW_AVAILABLE_COURSES="select * from course where courseId not in  (select courseId  from registeredcourse where studentId = ?) and seats > 0";
		public static final String CHECK_COURSE_AVAILABILITY=" select courseId from registeredcourse where studentId = ? ";
		public static final String DECREMENT_COURSE_SEATS="update course set seats = seats-1 where courseId = ? ";
		public static final String ADD_COURSE="insert into registeredcourse (studentId,courseId,grade) values ( ? , ?, ?)";
		public static final String DROP_COURSE_QUERY = "delete from registeredcourse where courseId = ? AND studentId = ?;";
		public static final String INCREMENT_SEAT_QUERY  = "update course set seats = seats + 1 where  courseId = ?;";
		public static final String CALCULATE_FEES  = "select sum(courseFee) from course where courseId in (select courseId from registeredcourse where studentId = ?);";
		public static final String VIEW_GRADE = "select course.courseId,course.courseName,registeredcourse.grade from course inner join registeredcourse on course.courseId = registeredcourse.courseId where registeredcourse.studentId = ?;";	
		public static final String GET_SEATS = "select seats from course where courseId = ?;";
		public static final String INSERT_PAYMENT = "insert into payment(studentId,modeofPayment,referenceId,amount) values(?,?,?,?);";
		public static final String INSERT_NOTIFICATION = "insert into notification(studentId,type,referenceId) values(?,?,?);";
		public static final String GET_NOTIFICATION = "select * from notification where referenceId = ?;";
		public static final String GET_REGISTRATION_STATUS=" select isRegistered from student where studentId = ? ";
		public static final String SET_REGISTRATION_STATUS="update student set isRegistered = true  where studentId=?";
		public static final String GET_PAYMENT_STATUS=" select isPaid from student where studentId = ? ";
		public static final String SET_PAYMENT_STATUS="update student set isPaid = true  where studentId=?";
		public static final String NUMBER_OF_REGISTERED_COURSES=" select studentId from registeredcourse where studentId = ? ";
		public static final String IS_REGISTERED=" select courseId from registeredcourse where courseId=? and studentId=? ";
		public static final String GET_PAYMENT_UUID = "select referenceId from notification where notificationId=?";
		public static final String GET_GRADES = "select grade from registeredcourse where studentId=?";
		
		//ProfessorDao queries
	
		public static final String UPDATE_PASSWORD="update user set password=? where userId = ? ";
		public static final String VERIFY_CREDENTIALS="select password from user where userId = ?";
		public static final String GET_ROLE="select role from user where userId = ?";
		public static final String GET_COURSES="select * from course where professorId=?";
		public static final String GET_ENROLLED_STUDENTS="select course.courseId,course.courseName,registeredcourse.studentId from course inner join registeredcourse on course.courseId = registeredcourse.courseId where course.courseId=?";
		public static final String ADD_GRADE="update registeredcourse set grade=? where courseId=? and studentId=?";
		public static final String GET_PROF_NAME = "select name from user where userId = ?";
		public static final String REQUEST_COURSE_ASSIGNMENT = "insert into prof_course_request(userId,courseId) values(?,?)";
		public static final String GET_UNASSIGNED_COURSES = "select * from course where professorId=?";
		public static final String GET_PROF_COURSE_REQUESTS = "select userId from prof_course_request where courseId=?";
		
		
}
