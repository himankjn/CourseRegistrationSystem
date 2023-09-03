package com.wibmo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.wibmo.routeconfiguration.SpringCloudConfiguration;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@Import({SpringCloudConfiguration.class})
@EnableEurekaClient
public class CrsGroupbWibmoApiGatewayDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupbWibmoApiGatewayDemoApplication.class, args);
	}

}
