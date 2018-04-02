package com.info.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.info.model.DemandeEnCours;
import com.info.model.Ipv4range;
import com.info.model.Prefix;
import com.info.model.Response;
import com.info.model.Technologie;
import com.info.repo.DemandeEnCoursRepository;
import com.info.repo.Ipv4rangeRepository;
import com.info.repo.PrefixRepository;
import com.info.repo.ResponseRepository;
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
	
	@Autowired
	Ipv4rangeRepository ipvrepository;
	
	@Autowired
	ResponseRepository  responserepository ;

	
	
	
	
	//--------------------------------Demande en cours ----------------------------------------//
	
	
	
	//Save une demande 
	@RequestMapping("/demandeR/save")
	public String save(){
		demanderepo.save(new DemandeEnCours());
		
	
		return "Done";
	}
	
	
   //Supprimer une demande 
	@RequestMapping("/demande/delete")
	public String deleteById() {
          String result="" ;
		repository.deleteAll();
		return result="done";
		
	}
	
	
	
	//Demande View 
		@RequestMapping(value="/demande", method = RequestMethod.GET )
	    @ModelAttribute("prefixLst")
		public ModelAndView demandeResource(){
			ModelAndView modelAndView = new ModelAndView();
	    	List<Prefix> prefixLst = (List<Prefix>) repository.findAll();
	    	List<Technologie> technooLst = (List<Technologie>) techrepository.findAll();

			DemandeEnCours demande = new DemandeEnCours();
			modelAndView.addObject("demande", demande);
			modelAndView.addObject("prefixLst",prefixLst);
			modelAndView.addObject("technooLst",technooLst);

			modelAndView.setViewName("technical/demande");
			return modelAndView;
		}
	
	// create new demande
		@RequestMapping(value = "/demande", method = RequestMethod.POST)
		public ModelAndView createNewdemande(@Valid DemandeEnCours demande, BindingResult bindingResult , HttpServletRequest request) {
			ModelAndView modelAndView = new ModelAndView();
		    userService.savedemande(demande ,request);
			modelAndView.addObject("successMessage", "Votre demande est ajoutée avec succés");
			modelAndView.addObject("demande", new DemandeEnCours());
			modelAndView.setViewName("technical/demande");
			
		
		return modelAndView;
	}
	
	

	
		
	
	//ajouter une  demande et créer une nouvelle reponse [Etape 3]
	@RequestMapping(value = "/ip/{id}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView SaveResponse(@PathVariable Long id,
       @ModelAttribute("iprange") Ipv4range ip4range,HttpServletRequest req,
       @ModelAttribute("demande") DemandeEnCours demande, Response response) {
	   
		
	   ModelAndView modelAndView = new ModelAndView();
	   List<Ipv4range> Listip = (List<Ipv4range>) ipvrepository.findAll();
		modelAndView.addObject("Listip", Listip);
	     
	      Long v=	(Long)req.getSession().getAttribute("id");
       //  System.out.println(v);
		 DemandeEnCours d= demanderepo.findOne((long) v); 
	    //  System.out.println(d);

	      Ipv4range d1= ipvrepository.findOne(ip4range.getId());
	      //System.out.println(d1);
   		
	      try{userService.repondredemande(ip4range,demande,response,req);
	      }catch(Exception e)
	      {System.out.println("is not a legal IPv4 address prefix");
			ModelAndView model= new ModelAndView();
			model.setViewName("admin/PrefixException");
			return model ;
			
	      }
		    List<DemandeEnCours> Listdemande= (List<DemandeEnCours>) demanderepo.findAll();
			modelAndView.addObject("Listdemande", Listdemande);

	      modelAndView.setViewName("admin/Listdemande");

		return modelAndView;
	}
	
	//ajouter une  demande[Etape 2]
		@RequestMapping(value = "/ip/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public ModelAndView Response(@PathVariable Long id,@ModelAttribute("demande") DemandeEnCours demande , Response response,
	       @ModelAttribute("iprange") Ipv4range ip4range,HttpServletRequest req, RedirectAttributes redir) {
		   ModelAndView modelAndView = new ModelAndView();
		   List<Ipv4range> Listip = (List<Ipv4range>) ipvrepository.findAll();
			  modelAndView.addObject("Listip", Listip);
			  DemandeEnCours d= demanderepo.findOne(demande.getId()); 
			      req.getSession().setAttribute("id",id);
			      req.setAttribute("id", id);
		      modelAndView.setViewName("ipvrange");
			  return modelAndView;
		}
		
	
	//ajouter une  demande  [Etape 1]
	@RequestMapping(value="/iprangelist/{id}" , method=RequestMethod.GET)
    @ModelAttribute("Listip")
	@ResponseBody
	public ModelAndView findAllIprange(@PathVariable Long id,HttpServletRequest req, RedirectAttributes redir,@ModelAttribute("demande") DemandeEnCours demande , Response response,HttpServletRequest request){
		
		ModelAndView modelAndView = new ModelAndView();
		
	     // System.out.println("/////////////////////////////////////");

		  DemandeEnCours d= demanderepo.findOne((long)id); 
	      //System.out.println(d);
	  
		      
		      modelAndView.setViewName("redirect:/ip/{id}");
		      redir.addFlashAttribute("id",id);

		   
		      return  modelAndView;


		
		  
	}

	//Afficher la liste des demandes En cours 

			@RequestMapping("/demande/status")
		    @ModelAttribute("Listdemande")
			public ModelAndView findbyStatus(String Status){
				ModelAndView modelAndView = new ModelAndView();

				String S = "EN COURS";
				List<DemandeEnCours> Listdemande = (List<DemandeEnCours>) demanderepo.findByStatus(S);
				modelAndView.addObject("Listdemande",Listdemande);
				modelAndView.setViewName("admin/Listdemande");

				
				
				return modelAndView;
				
				
				
			}
	//Afficher la liste des demandes Traitées 

			@RequestMapping("/demandetraitée")
		    @ModelAttribute("Listdemande")
			public ModelAndView lesdemandesTraitées(String Status){
				ModelAndView modelAndView = new ModelAndView();

				String S = "traitée";
				List<DemandeEnCours> Listdemande = (List<DemandeEnCours>) demanderepo.findByStatus(S);
				modelAndView.addObject("Listdemande",Listdemande);
				modelAndView.setViewName("admin/ListDemandeTraités");

				
				
				return modelAndView;
				
				
				
			}
			
}
