package com.wibmo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Student;
import com.wibmo.business.StudentServiceInterface;
import com.wibmo.business.UserServiceInterface;
import com.wibmo.exception.RoleMismatchException;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;

/*
 * Rest Controller to handle admin operations
 */
@RestController
@RequestMapping(value="/auth")
public class AuthController {
	@Autowired
	private StudentServiceInterface studentService;
	@Autowired
	private UserServiceInterface userService;
	
//	{
//        "userId": "parth123@gmail.com",
//        "password": "parth123",
//        "gender": "MALE",
//        "role": "STUDENT",
//        "address": "valsad",
//        "name": "Parth",
//        "department": null,
//        "studentId": "parth123@gmail.com",
//        "gradYear": 0,
//        "approved": false
//    }
	/**
	 * Registers new student and sends approval request to admin
	 * @return ResponseEntity
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity registerStudent(@RequestBody Student student) {
		try {
			studentService.register(student.getName(), student.getUserId(), 
					student.getPassword(), student.getGender(), student.getGradYear(),
					student.getDepartment(), student.getAddress());
			return new ResponseEntity("Student registered with id: "+student.getStudentId(),HttpStatus.OK);
		}
		catch(StudentNotRegisteredException ex)
		{
			return new ResponseEntity("error while registering student!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	/**
	 * authentication method for student/admin/professor
	 */
	@RequestMapping(value="/login/{userId}/{pass}/{role}",method=RequestMethod.GET)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity loginUser(@PathVariable("userId") String userId, @PathVariable("pass") String password, @PathVariable("role") String roleInp)
	{
		boolean loggedin=false;
		try {
			loggedin = userService.verifyCredentials(userId, password);
		} catch (UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(loggedin)
		{
			try {
				userService.verifyUserRole(userId,roleInp);
				if(roleInp.equals("STUDENT")) {
					boolean isApproved=studentService.isApproved(userId);
					if(!isApproved) {
						return new ResponseEntity("You have not been approved by Admin yet!",HttpStatus.INTERNAL_SERVER_ERROR);
						
					}
				}
				return new ResponseEntity("Logged in as "+roleInp,HttpStatus.OK);
				}
			catch(RoleMismatchException e) {
				return new ResponseEntity("RoleMismatch",HttpStatus.NOT_ACCEPTABLE);
			}
			
		}
		else
		{
			return new ResponseEntity("Invalid Credentials!",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * update password of User
	 */
	
	@RequestMapping(value="updatePassword/{id}/{pass}",method=RequestMethod.PUT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity updatePassword(@PathVariable("id") String userId,@PathVariable("pass") String password) {
			boolean isUpdated=userService.updatePassword(userId, password);
			if(isUpdated) {
				return new ResponseEntity("Password Updated!",HttpStatus.OK);
			}
			else {
				return new ResponseEntity("Unable to update password",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
	
}
