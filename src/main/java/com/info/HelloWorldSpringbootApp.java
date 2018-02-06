package com.info;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan(basePackages={"com.info.controller"})
public class HelloWorldSpringbootApp {
	   
	//public static void main(String[] args) {      
	/*  try {
	      Class.forName("org.postgresql.Driver");
	      System.out.println("Driver O.K.");

	      String url = "jdbc:postgresql://localhost:5432/testdb";
	      String user = "postgres";
	      String passwd = "pg123456";

	      Connection conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("Connexion effective !");         
	         
	    } catch (Exception e) {
	      e.printStackTrace();
	    }      
	  }*/
	   public static void main(String[] args) throws Exception {
	        SpringApplication.run(HelloWorldSpringbootApp.class, args);
	    }
}

