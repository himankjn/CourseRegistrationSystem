package com.wibmo.business;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.wibmo.business.StudentServiceImpl;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.dao.StudentDAOInterface;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.bean.Student;

public class StudentServiceTest {

    private StudentServiceImpl studentService;

    @Before
    public void setUp() {
        studentService = StudentServiceImpl.getInstance();
    }
    

//    public void testRegisterStudent() throws StudentNotRegisteredException {
//        String studentId = studentService.register("Test Student", "testUserId", "password", GenderConstant.MALE,
//                2023, "Test Branch", "Test Address");
//
//        assertNotNull(studentId);
//    }

    @Test(expected= StudentNotRegisteredException.class)
    public void testRegisterStudentException() throws StudentNotRegisteredException {
        studentService.register("Test Student", "testUserId", "password", GenderConstant.MALE,
                2023, "Test Branch", "Test Address");
    }

    @Test
    public void testIsApproved() {
        boolean approved = studentService.isApproved("testStudentId");

        assertFalse(approved);
    }
}
