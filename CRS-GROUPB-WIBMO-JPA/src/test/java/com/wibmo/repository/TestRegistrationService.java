package com.wibmo.repository;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.wibmo.service.Impl.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestRegistrationService {

	@InjectMocks
	private AdminServiceImpl adminService;
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Mock
	StudentRepository studentRepository;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testAddCourse() {
		
	}

}
