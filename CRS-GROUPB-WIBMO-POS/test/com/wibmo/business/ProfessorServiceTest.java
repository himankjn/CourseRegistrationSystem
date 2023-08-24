/**
 * 
 */
package com.wibmo.business;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.wibmo.dao.ProfessorDAOImpl;
import com.wibmo.dao.ProfessorDAOInterface;
import com.wibmo.exception.InvalidCourseAssignmentRequestException;
import com.wibmo.bean.Course;
import com.wibmo.bean.EnrolledStudent;

public class ProfessorServiceTest {

    private ProfessorServiceInterface professorService;

    @Before
    public void setUp() throws Exception {
       
        professorService = ProfessorServiceImpl.getInstance();
    }

    @Test
    public void testSubmitGrade() {
        String studentId = "himank123@gmail.com";
        String courseCode = "SPE";
        String grade = "B";
        boolean result = professorService.submitGrade(studentId, courseCode, grade);
        assertTrue(result);

    }

    @Test
    public void testViewEnrolledStudents() {
        String courseId = "SPE";
        List<EnrolledStudent> enrolledStudents = professorService.viewEnrolledStudents(courseId);
        List<String> actual=new ArrayList<String>();
        for(EnrolledStudent student:enrolledStudents) {
        	actual.add(student.getStudentId());
        }
        List<String>expected = Arrays.asList("himank123@gmail.com","parth123@gmail.com");
        assertEquals(expected,actual);
    }

    @Test
    public void testViewAssignedCourses() {
        String profId = "bob123@gmail.com";
        List<Course> assignedCourses = professorService.viewAssignedCourses(profId);
        List<String> actual=new ArrayList<String>();
        List<String> expected = Arrays.asList("CPP","CR","DL101","ML101","NW");
        for(Course course:assignedCourses) {
        	actual.add(course.getCourseId());
        }
        assertEquals(expected,actual);
        
    }

    @Test
    public void testGetUnassignedCourses() {
        List<Course> unassignedCourses = professorService.getUnassignedCourses();
        List<String> actual=new ArrayList<String>();
        List<String> expected=Arrays.asList("CS10","JV","PY","SPE");
        for(Course course:unassignedCourses)
        	actual.add(course.getCourseId());
        assertEquals(expected,actual);

    }

    @Test
    public void testRequestCourseAssignment() {
        String userId = "bob123@gmail.com";
        String courseId = "CS10";
        boolean result = professorService.requestCourseAssignment(userId, courseId);
        assertTrue(result); 

    }

}
