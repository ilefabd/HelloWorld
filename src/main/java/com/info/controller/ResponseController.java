package com.info.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.DemandeEnCours;
import com.info.model.Invoice;
import com.info.model.Response;
import com.info.model.User;
import com.info.repo.DemandeEnCoursRepository;
import com.info.repo.InvoiceRepository;
import com.info.repo.ResponseRepository;
import com.info.repo.UserRepository;
import com.info.service.UserService;
@Controller
public class ResponseController {
	@Autowired 
	DemandeEnCoursRepository demanderepo ;
	@Autowired 
	ResponseRepository responserepository ;
	@Autowired
	UserRepository userrepo;
	@Autowired
ResponseRepository repository ;	
	@Autowired
	UserService userservice ;
			//Afficher la liste de demandes
			@RequestMapping(value="/response" , method=RequestMethod.GET)
		    @ModelAttribute("Listdemande")
			public ModelAndView findAllDemande(){
				ModelAndView modelAndView = new ModelAndView();

				List<DemandeEnCours> Listdemande = (List<DemandeEnCours>) demanderepo.findAll();
				modelAndView.addObject("Listdemande", Listdemande);
				
				modelAndView.setViewName("admin/Listdemande");

				return modelAndView ;
			}
			
			//Supprimer une demande 
			@RequestMapping("/response/delete")
			public String deleteById() {
		          String result="" ;
				responserepository.deleteAll();
				return result="done";
				
			}
			@RequestMapping(value="/Myresponse/afficher", method = RequestMethod.GET)
			public ModelAndView MyInvoice(HttpServletRequest request){
				ModelAndView modelAndView =new ModelAndView() ;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				User user1 = userservice.findUserByEmail(auth.getName());
				modelAndView.addObject("userName", user1.getName() + " " + user1.getLastName());
				  Principal principal = request.getUserPrincipal();
			      String email = principal.getName();
			      User user =  userrepo.findByEmail(email)  ;
			      String org = user.getOrganisation();

				List<Response> Listresponse = (List<Response>) repository.findbyOrganisation(org);
				modelAndView.addObject("Listresponse",Listresponse);
				modelAndView.setViewName("Technical/ListResponseOrg");
				
				
				return modelAndView ;
			}
}
