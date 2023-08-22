package com.wibmo.controller;


import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Course;
import com.wibmo.business.AdminServiceInterface;

@RestController
public class AdminController {
	private static final Logger logger= LogManager.getLogger(AdminController.class);
	
	
	@Autowired
	private AdminServiceInterface adminService;

	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/hello-world")
	public String helloWorld() {	
		return "Hello World";
	}
	
	@GetMapping("/viewCoursesInCatalogue")
	public List<Course> viewCoursesInCatalogue() {
		logger.info("Retrieving courses from catalog.");
		List<Course> courseList = adminService.viewCourses();
		return courseList;
	}
	
	
	

}
