package com.wibmo.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.entity.Student;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.service.Impl.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestStudentService {

	@InjectMocks
	StudentServiceImpl studentService;
	
	@Mock
	StudentRepository studentRepository;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testRegister() throws StudentNotRegisteredException{
		Student newStudent = new Student();
		
		newStudent.setAddress("Hyd");
		newStudent.setApproved(false);
		newStudent.setDepartment("CSE");
		newStudent.setGender(GenderConstant.FEMALE);
		newStudent.setGradYear(0);
		newStudent.setName("Avanthi");
		newStudent.setPaid(false);
		newStudent.setPassword("Avanthi@123");
		newStudent.setRegistered(false);
		newStudent.setReportGenerated(false);
		newStudent.setRole(RoleConstant.PROFESSOR);
		newStudent.setStudentId("Avanthi22@yahoo.com");
		newStudent.setUserId("Avanthi22@yahoo.com");
		
		when(studentRepository.save(newStudent)).thenReturn(newStudent);
		
		String actualStudentId = studentService.register(newStudent);
		String expectedStudentId = "Avanthi22@yahoo.com";
		
		assertEquals(actualStudentId,expectedStudentId);
	}
	
	@Test
	public void testGetStudentId() {
		String userId = "Avanthi22@yahoo.com";
		
		Optional<Student> newStudent = Optional.ofNullable(new Student());
		
		newStudent.get().setAddress("Hyd");
		newStudent.get().setApproved(false);
		newStudent.get().setDepartment("CSE");
		newStudent.get().setGender(GenderConstant.FEMALE);
		newStudent.get().setGradYear(0);
		newStudent.get().setName("Avanthi");
		newStudent.get().setPaid(false);
		newStudent.get().setPassword("Avanthi@123");
		newStudent.get().setRegistered(false);
		newStudent.get().setReportGenerated(false);
		newStudent.get().setRole(RoleConstant.PROFESSOR);
		newStudent.get().setStudentId("Avanthi22@yahoo.com");
		newStudent.get().setUserId("Avanthi22@yahoo.com");
		
		when(studentRepository.findByUserId(userId)).thenReturn(newStudent.get());
		String actualStudentIdGot = studentService.getStudentId(userId);
		String expectedStudentId = "Avanthi22@yahoo.com";
		
		assertEquals(actualStudentIdGot,expectedStudentId);
	}
	
	@Test
	public void testIsApproved() {
		String studentId = "Avanthi22@yahoo.com";
		
		Student newStudent = new Student();
		
		newStudent.get().setAddress("Hyd");
		newStudent.get().setApproved(false);
		newStudent.get().setDepartment("CSE");
		newStudent.get().setGender(GenderConstant.FEMALE);
		newStudent.get().setGradYear(0);
		newStudent.get().setName("Avanthi");
		newStudent.get().setPaid(false);
		newStudent.get().setPassword("Avanthi@123");
		newStudent.get().setRegistered(false);
		newStudent.get().setReportGenerated(false);
		newStudent.get().setRole(RoleConstant.PROFESSOR);
		newStudent.get().setStudentId("Avanthi22@yahoo.com");
		newStudent.get().setUserId("Avanthi22@yahoo.com");
		
		when(studentRepository.findById(studentId).get()).thenReturn(newStudent.get());
		
		boolean expectedApprovedStatus = false;
		boolean actualApprovedStatus = studentService.isApproved(studentId);
		
		assertEquals(expectedApprovedStatus,actualApprovedStatus);
	}

	
}
