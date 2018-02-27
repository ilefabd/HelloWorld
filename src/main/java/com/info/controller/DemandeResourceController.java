package com.info.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.Customer;
import com.info.model.DemandeEnCours;
import com.info.model.Prefix;
import com.info.model.Response;
import com.info.model.Technologie;
import com.info.model.User;
import com.info.repo.DemandeEnCoursRepository;
import com.info.repo.PrefixRepository;
import com.info.repo.TechnologieRepository;
import com.info.repo.UserRepository;
import com.info.service.UserService;

@RestController
public class DemandeResourceController {
	@Autowired
	PrefixRepository repository;
	
	@Autowired 
	DemandeEnCoursRepository demanderepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepository ;
	
	@Autowired
	TechnologieRepository techrepository;
	
	//--------------------------------Prefix----------------------------------------//
	
	
	//Save Prefix 
	@RequestMapping("/prefix/save")
	public String process(){
		// save a single Customer
		repository.save(Arrays.asList(new Prefix("/24"), new Prefix("/12"),
				new Prefix("/16"), new Prefix("/14")));
		
		
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
	//--------------------------------Demande en cours ----------------------------------------//
	
	
	//Afficher la liste de demandes
	@RequestMapping("/demande/findall")
	public String findAllDemande(){
		String result = "";
		
		for(DemandeEnCours pre : demanderepo.findAll()){
			result += pre.toString() + "<br>";
		}
		
		return result;
	}
	
	//Save une demande 
	@RequestMapping("/demandeR/save")
	public String save(){
		demanderepo.save(new DemandeEnCours());
		
	
		return "Done";
	}
	
	
   //Supprimer une demande 
	@RequestMapping("/demande/delete")
	public String deleteById() {
		String result ="";
		repository.deleteAll();
		return result="done";
		
	}
	
	
	
	//Demand View 
		@RequestMapping(value="/demande", method = RequestMethod.GET )
		public ModelAndView demandeResource(){
			ModelAndView modelAndView = new ModelAndView();
			DemandeEnCours demande = new DemandeEnCours();
			modelAndView.addObject("demande", demande);
			modelAndView.setViewName("demande");
			return modelAndView;
		}
	
	// create new demand
		@RequestMapping(value = "/demande", method = RequestMethod.POST)
		public ModelAndView createNewdemande(@Valid DemandeEnCours demande, BindingResult bindingResult , HttpServletRequest request) {
			ModelAndView modelAndView = new ModelAndView();
		    userService.savedemande(demande ,request);
			modelAndView.addObject("successMessage", "demande has been registered successfully");
			modelAndView.addObject("demande", new DemandeEnCours());
			modelAndView.setViewName("demande");
			
		
		return modelAndView;
	}
	
	

		//--------------------------------Response----------------------------------------//
	
	//create new response 
	@RequestMapping(value = "/response", method = RequestMethod.POST)
    @ResponseBody
    public String SaveResponse(Response response) {
       
		userService.repondredemande(response);
		return "done";
	}
	
	
}
