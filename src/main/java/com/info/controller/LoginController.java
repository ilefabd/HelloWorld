package com.info.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.info.PdfGenaratorUtil;
import com.info.model.Organisation;
import com.info.model.Ticket;
import com.info.model.User;
import com.info.repo.OrganisationRepository;
import com.info.repo.TicketRepository;
import com.info.repo.UserRepository;
import com.info.service.UserService;



@RestController
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepository ;
	@Autowired
	OrganisationRepository orgrepo ;
	@Autowired
	TicketRepository repository ;
	//--------------------------------Login View----------------------------------------//

		@RequestMapping(value={"/login"}, method = RequestMethod.GET)
		public ModelAndView login(){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("index");
			return modelAndView;
		}
		@RequestMapping(value={"/facture"}, method = RequestMethod.GET)
		public ModelAndView facture(){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("facture");
			return modelAndView;
		}
		//--------------------------------Login View----------------------------------------//

				@RequestMapping(value={"/","/index"}, method = RequestMethod.GET)
				public ModelAndView index(){
					ModelAndView modelAndView = new ModelAndView();
					modelAndView.setViewName("index");
					return modelAndView;
				}
				//--------------------------------Login View----------------------------------------//

				@RequestMapping(value={"/blog"}, method = RequestMethod.GET)
				public ModelAndView blog(){
					ModelAndView modelAndView = new ModelAndView();
					modelAndView.setViewName("blog-single");
					return modelAndView;
				}
	
	//-------------------------------- registration view ----------------------------------------//

	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/registration/financier", method = RequestMethod.GET)
    @ModelAttribute("Orglist")
	public ModelAndView registrationFinancier(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user1 = userService.findUserByEmail(auth.getName());
		User user = new User();
		List<Organisation> Orglist = (List<Organisation>) orgrepo.findAll();
		modelAndView.addObject("Orglist", Orglist);

		modelAndView.addObject("user", user);
    	modelAndView.addObject("userName", user1.getName() + " " + user1.getLastName());

		modelAndView.setViewName("registrationF");
		return modelAndView;
	}
	
	
	
	
	@RequestMapping(value = "/registration/Financier", method = RequestMethod.POST)
    @ModelAttribute("Orglist")
	public ModelAndView createNewFinancier(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registrationF");
		} else {
			userService.saveFinacier(user);
			modelAndView.addObject("successMessage", "Le financier a été enregistré avec succès");
			modelAndView.addObject("user", new User());
			List<Organisation> Orglist = (List<Organisation>) orgrepo.findAll();
			modelAndView.addObject("Orglist", Orglist);
			modelAndView.setViewName("registrationF");
			
		}
		return modelAndView;
	}
	@RequestMapping(value="/registration/technical", method = RequestMethod.GET)
    @ModelAttribute("Orglist")
	public ModelAndView registrationTechnical(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user1 = userService.findUserByEmail(auth.getName());
		User user = new User();
		List<Organisation> Orglist = (List<Organisation>) orgrepo.findAll();
		modelAndView.addObject("Orglist", Orglist);
		modelAndView.addObject("user", user);
    	modelAndView.addObject("userName", user1.getName() + " " + user1.getLastName());

		modelAndView.setViewName("registrationT");
		return modelAndView;
	}
    @SuppressWarnings("unused")
    @RequestMapping(value = "/registration/technical", method = RequestMethod.POST)
	public ModelAndView createNewTechnical(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveTechnical(user);
			modelAndView.addObject("successMessage", "L'opérateur a été enregistré avec succès");
			modelAndView.addObject("user", new User());
			List<Organisation> Orglist = (List<Organisation>) orgrepo.findAll();
			modelAndView.addObject("Orglist", Orglist);
			modelAndView.setViewName("registrationT");
			
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/gotoNextPage",method = RequestMethod.GET)
	public  ModelAndView gotoNextPage(HttpServletRequest request, HttpServletResponse response){
	    System.out.println("Inside gotoNextPage!!!!!!");

	    ModelMap model = new ModelMap();
	    model.addAttribute("message", "next page");
	    
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
	    return new ModelAndView(
	       new RedirectView("/technical/home", true),
	       //or new RedirectView("/nextpage.html", true),
	       model
	    );
	
	
	}
	//-------------------------------- admin home view ----------------------------------------//

		@RequestMapping(value="/admin/home", method = RequestMethod.GET)
		public ModelAndView home(){
			ModelAndView modelAndView = new ModelAndView();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			User user = userService.findUserByEmail(auth.getName());
			int status=0;
			if(user.getActive()== status)
			{
				ModelAndView model= new ModelAndView();
				System.out.println("you have an administrative problem");
				model.setViewName("default");
				return model ;

			} else  
				userService.stat();
			//pie chart 
	        float v = (float) 21.28 ;
	        float v1 = (float) 17.38 ;
	        float v2 = (float) 11.63 ;
	        float v3 = (float) 10.10 ;
	        float v4 = (float) 4.49 ;
	        float autre = (float)10.12  ;
	        float ATI = (float) 25 ;

	        modelAndView.addObject("Ooredoo", v);
	        modelAndView.addObject("Orange", v1);
	        modelAndView.addObject("Topnet", v2);
	        modelAndView.addObject("TunisieTelecom", v3);
	        modelAndView.addObject("Globalnet", v4);
	        modelAndView.addObject("autre", autre);
	        modelAndView.addObject("ATI", ATI);
			modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
			modelAndView.addObject("adminMessage","Accueil Administrateur");
			
			 return  modelAndView;
				      
			
			
			
			
		}
	
	
	//-------------------------------- financier home view ----------------------------------------//

	@RequestMapping(value="/financier/home", method = RequestMethod.GET)
	public ModelAndView homef(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName",   user.getName() + " " + user.getLastName() );
		modelAndView.addObject("finanMessage","Accueil Financier");
		 Principal principal = request.getUserPrincipal();
	      String email = principal.getName();

		List<Ticket> Listticket = (List<Ticket>) repository.findbyEmail(email);
		modelAndView.addObject("Listticket",Listticket);
		modelAndView.setViewName("financier/home");
		return modelAndView;
	}
	//-------------------------------- technical home view ----------------------------------------//

		@RequestMapping(value="/technical/home", method = RequestMethod.GET)
		public ModelAndView homeT(HttpServletRequest request){
			ModelAndView modelAndView = new ModelAndView();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			modelAndView.addObject("userName", user.getName() + " " + user.getLastName() );
			modelAndView.addObject("techniMessage","Accueil Opérateur");
			Principal principal = request.getUserPrincipal();
		    String email = principal.getName();

			List<Ticket> Listticket = (List<Ticket>) repository.findbyEmail(email);
			modelAndView.addObject("Listticket",Listticket);;
			modelAndView.setViewName("technical/home");
			return modelAndView;
		}
	//-------------------------------- default view ----------------------------------------//

	@RequestMapping(value="/default", method = RequestMethod.GET)
	public ModelAndView defaultuser(){
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("default");
		return modelAndView;
	}
	
	//-------------------------------- user authenticated name  ----------------------------------------//

	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
         String name = principal.getName();
         User user =  userRepository.findByEmail(name)  ;
         String org = user.getLastName();
        return  name + org ;
	}
	
	@Autowired
	//PdfGenaratorUtil pdfGenaratorUtil;
	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView pdf() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		 Map<String,String> data = new HashMap<String,String>();
		    data.put("name","ilef");
		    data.put("prenom","Abd");
		  //  pdfGenaratorUtil.createPdf("pdf",data); 
		    modelAndView.addObject("name" ,data);
		    modelAndView.addObject("prenom" ,data);

		modelAndView.setViewName("pdf");
		return modelAndView;  
	}
	@RequestMapping(value = "/profil", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView profil(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
        Principal principal = request.getUserPrincipal();
         String name = principal.getName();
         User user =  userRepository.findByEmail(name)  ;
         String org = user.getLastName();
         model.addObject("name", user.getName());
         model.addObject("firstname", user.getLastName());
         model.addObject("email", user.getEmail());
         model.addObject("org", user.getOrganisation());
model.setViewName("/profil");

        return  model ;
	}
	}		
        		
        		