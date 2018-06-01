package com.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.DemandeEnCours;
import com.info.repo.DemandeEnCoursRepository;
import com.info.repo.ResponseRepository;
@Controller
public class OrganisationController {
	@Autowired 
	
	
			//Afficher la liste de demandes
			@RequestMapping(value="/organisation" , method=RequestMethod.GET)
		    //@ModelAttribute("Listdemande")
			public ModelAndView findAllDemande(){
				ModelAndView modelAndView = new ModelAndView();

				//List<DemandeEnCours> Listdemande = (List<DemandeEnCours>) demanderepo.findAll();
				//modelAndView.addObject("Listdemande", Listdemande);
				
				//modelAndView.setViewName("admin/Listdemande");

				return modelAndView ;
			}
			
			
}
