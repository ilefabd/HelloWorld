package com.info;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan(basePackages={"com.info.controller","com.info.service","com.info","com.info.controller.DemandeResourceController"})
@EnableJpaRepositories("com.info.repo")
@EntityScan("com.info.model")
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

