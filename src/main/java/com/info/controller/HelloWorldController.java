package com.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.model.Customer;
import com.info.repo.CustomerRepository;
 

@RestController
public class HelloWorldController {
	CustomerRepository repository;
	
	@RequestMapping("/save")
	public String process(){
		// save a single Customer
		repository.save(new Customer("ileftest", "abde"));
		
		// save a list of Customers
		repository.save(Arrays.asList(new Customer("ilef", "abderrahim"), new Customer("tarek", "ghodhbani"),
										new Customer("touma", "abderrahim"), new Customer("tarek", "mansour")));
		
		return "Done";
	}
	
	
	@RequestMapping("/findall")
	public String findAll(){
		String result = "";
		
		for(Customer cust : repository.findAll()){
			result += cust.toString() + "<br>";
		}
		
		return result;
	}
	
	@RequestMapping("/findbyid")
	public String findById(@RequestParam("id") long id){
		String result = "";
		result = repository.findOne(id).toString();
		return result;
	}
	
	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("lastname") String lastName){
		String result = "";
		
		for(Customer cust: repository.findByLastName(lastName)){
			result += cust.toString() + "<br>"; 
		}
		
		return result;
	}
	
@RequestMapping("/delete")
public String deleteById(@RequestParam("id") long id) {
	String result ="";
	repository.delete(id);
	return result="done";
	
}
@RequestMapping("update")
public String update(@RequestParam("id") long id) {
	
Customer c = repository.findOne(id);
c.setFirstName("dada");
c.setLastName("bouazzi");
repository.save(c);
return "done";
}



		
		
		
		
		

	
	
}
