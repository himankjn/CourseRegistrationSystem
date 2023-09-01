package com.wibmo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@EnableAutoConfiguration
@Configuration
@ComponentScan("com.wibmo.*")
@EnableEurekaClient
@SpringBootApplication
public class CrsGroupbWibmoStudentMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupbWibmoStudentMicroserviceApplication.class, args);
	}

}
