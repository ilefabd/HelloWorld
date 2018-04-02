package com.info.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.model.Technologie;
import com.info.repo.TechnologieRepository;

@Controller
public class TechnologieController {

	
	@Autowired
    TechnologieRepository repository;
	//-------------------------------- Test Crud ----------------------------------------//

	//save technologie
	@RequestMapping("/technologie/save")
	public String process(){
		// save a single Customer
		repository.save(Arrays.asList(new Technologie("ADSL"), new Technologie("3G"),
				new Technologie("Fibre"), new Technologie("Satellite")));
		
		
		return "Done";
	}
	
	//afficher la liste de technologies
	@RequestMapping("/technologie/findall")
	public String findAll(){
		String result = "";
		
		for(Technologie tech: repository.findAll()){
			result += tech.toString() + "<br>";
		}
		
		return result;
	}
	 
	
}
//------------------------------------------Technologie View -------------------------------------------------//