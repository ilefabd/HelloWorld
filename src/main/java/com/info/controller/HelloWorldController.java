package com.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.lang.Object;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.model.Organisation;

import com.info.repo.OrganisationRepository;
 

@RestController
public class HelloWorldController {
	@Autowired
	OrganisationRepository repository;
	
	@RequestMapping("/organisation/save")
	public String process(){
		// save a single Customer
		
		// save a list of Customers
		repository.save(new Organisation((long) 1,"Orange",null, null, null, null));
		return "done" ;
	}
	
	
	@RequestMapping("/findall")
	public String findAll(){
		String result = "";
		
		for(Organisation cust : repository.findAll()){
			result += cust.toString() + "<br>";
		}
		
		return result;
	}
	
	@RequestMapping("/findbyid")
	public String findById(@RequestParam("organisation") long id){
		String result = "";
		result = repository.findOne(id).toString();
		return result;
	}
	
	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("orgname") String org_name){
		String result = "";
		
		for(Organisation cust: repository.findByorgname(org_name)){
			result += cust.toString() + "<br>"; 
		}
		
		return result;
	}
	
@RequestMapping("/delete")
public String deleteById(@RequestParam("organisation") long id) {
	String result ="";
	repository.delete(id);
	return result="done";
	
}
@RequestMapping("update")
public String update(@RequestParam("organisation") long id) {
	
Organisation c = repository.findOne(id);

;
return "done";
}
@RequestMapping("subnet")
public String subnet () {
return "done";
}


		
		
		
		
		

	
	
}
