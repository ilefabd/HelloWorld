package com.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan(basePackages={"com.info.controller"})
public class HelloWorldSpringbootApp {
	   

	    public static void main(String[] args) throws Exception {
	        SpringApplication.run(HelloWorldSpringbootApp.class, args);
	    }
}

