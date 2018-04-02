package com.info.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.Prefix;
import com.info.model.Technologie;
import com.info.repo.PrefixRepository;
import com.info.repo.TechnologieRepository;

@Controller
public class PrefixController {
	@Autowired
	PrefixRepository repository;
	
	@Autowired
	TechnologieRepository techrepository;
	//--------------------------------Prefix----------------------------------------//
	
	
	//Save Prefix 
	@RequestMapping("/prefix/save")
	public String process(){
		// save a single Customer
		repository.save(Arrays.asList(new Prefix("24"), new Prefix("18"),
				new Prefix("17"), new Prefix("16"),new Prefix("14"),new Prefix("12"),new Prefix("10")));
		
		
		return "Done";
	}
	
	//Afficher la liste de prefix

	@RequestMapping("/prefix/findall")
	public String findAll(){
		String result = "";
		
		for(Prefix pre : repository.findAll()){
			result += pre.toString() + "<br>";
		}
		
		
		System.out.println(result);
		return result;
	}
	
	//Find prefix by id 
	@RequestMapping("/prefix/findbyid")
	public Prefix findById(@RequestParam("id") Long id){
		Prefix p = repository.findOne(id);
		return p;
	}
	 
	// show data 
	@RequestMapping("/showdata")
	private void showData(){
    	// get All data
    	List<Prefix> prefixLst = (List<Prefix>) repository.findAll();
        List<Technologie> technologieLst =(List<Technologie>) techrepository.findAll();
         
        System.out.println("===================PREFIX LIST:==================");
      prefixLst.forEach(System.out::println);
         
        System.out.println("===================tECHNOLOGIE LIST:==================");
        technologieLst.forEach(System.out::println);
    }
	
	// Afficher la liste des prefix
	    @RequestMapping(value="/allprefix" , method = RequestMethod.GET)
	    @ModelAttribute("prefixLst")
		private ModelAndView prefixLst( ModelAndView model){
	    	// get All data
	    	List<Prefix> prefixLst = (List<Prefix>) repository.findAll();
           model.addObject("prefixLst",prefixLst);
	    		return model ;
	   
	    
	    }
	  //Supprimer une demande 
		@RequestMapping("/prefix/delete")
		public String deleteprefix() {
	          String result="" ;
			repository.deleteAll();
			return result="done";
			
		}    
		
	
}
