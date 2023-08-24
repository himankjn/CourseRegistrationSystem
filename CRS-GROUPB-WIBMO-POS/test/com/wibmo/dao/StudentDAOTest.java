package com.wibmo.dao;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wibmo.bean.Student;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.exception.StudentNotRegisteredException;

public class StudentDAOTest {
	private static StudentDAOInterface studentDao=null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		studentDao= StudentDAOImpl.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


//  public void testRegisterStudent() throws StudentNotRegisteredException {
//      String studentId = studentService.register("Test Student", "testUserId", "password", GenderConstant.MALE,
//              2023, "Test Branch", "Test Address");
//
//      assertNotNull(studentId);
//  }

  @Test(expected= StudentNotRegisteredException.class)
  public void testRegisterStudentException() throws StudentNotRegisteredException {
      studentDao.addStudent(new Student("testUserId", "Test Student",RoleConstant.STUDENT, "password", GenderConstant.MALE,
    		  "Test Address", "Test Branch","testUserId", 2023,false ));
  }

  @Test
  public void testIsApproved() {
      boolean approved = studentDao.isApproved("testStudentId");

      assertFalse(approved);
  }
}
