package com.wibmo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

@RequestMapping(value="/testStudent",method=RequestMethod.GET)
public String testStudent() {
	return "I'm Student Microservice";
}
}
