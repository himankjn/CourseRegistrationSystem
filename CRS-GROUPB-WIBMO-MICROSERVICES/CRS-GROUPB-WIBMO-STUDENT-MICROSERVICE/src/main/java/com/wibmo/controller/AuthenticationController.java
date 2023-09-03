package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.constants.NotificationTypeConstant;
import com.wibmo.entity.Student;
import com.wibmo.entity.User_Creds;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.security.JwtTokenUtil;
import com.wibmo.security.JwtUserDetailsService;
import com.wibmo.service.NotificationServiceInterface;
import com.wibmo.service.StudentServiceInterface;


/**
 * 
 */
@RestController
@RequestMapping(value = "/student")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private NotificationServiceInterface notificationService;
	
	@Autowired
	private StudentServiceInterface studentService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/login")
	public ResponseEntity login(@RequestBody User_Creds creds) {
		try {
			authenticate(creds.getUserName(), creds.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isApproved=studentService.isApproved(creds.getUserName());
		if(!isApproved) {
			return new ResponseEntity("You have not been approved by Admin yet!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(creds.getUserName());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}
	
	/**
	 * Registers new student and sends approval request to admin
	 * @return ResponseEntity
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity registerStudent(@RequestBody Student student) {
		try {
			studentService.register(student);
			notificationService.sendNotification(NotificationTypeConstant.REGISTRATION, student.getStudentId(), null, 0);
			return new ResponseEntity("Student registered with id: "+student.getUserId(),HttpStatus.OK);
		}
		catch(StudentNotRegisteredException ex)
		{
			return new ResponseEntity("error while registering student!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
