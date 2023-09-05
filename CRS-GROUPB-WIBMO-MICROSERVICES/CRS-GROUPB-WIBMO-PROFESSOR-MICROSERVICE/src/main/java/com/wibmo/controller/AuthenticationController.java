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

import com.wibmo.entity.User_Creds;
import com.wibmo.security.JwtTokenUtil;
import com.wibmo.security.JwtUserDetailsService;
import com.wibmo.service.UserServiceInterface;


/**
 * 
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping(value="/admin")
public class AuthenticationController {
	
	@Autowired
	private UserServiceInterface userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@RequestMapping(
		    method = RequestMethod.POST,
		    value = "/login")
	public ResponseEntity login(@RequestBody User_Creds creds) {
		try {
			if(!userService.verifyCredentials(creds.getUserName(), creds.getPassword())) {
				throw new Exception("Invalid Credential");
			}
			//userService.verifyUserRole(creds.getUserName(),creds.getRole());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(creds.getUserName());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}
}
