package com.info.controller;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.info.model.Invoice;
import com.info.model.Ipv4range;
import com.info.model.Response;
import com.info.model.Ticket;
import com.info.model.User;
import com.info.repo.TicketRepository;
import com.info.repo.UserRepository;
import com.info.service.UserService;


@RestController
public class TicketController  {

	@Autowired
	TicketRepository repository ;
	
	@Autowired
	UserRepository userrepository ;
/////////////////////////////Save////////////////////////////////////
	@Autowired
	UserRepository userrepo ;
	@Autowired
	UserService userservice ;
@RequestMapping("/ticket/save")
public String process() {



Date d = new Date() ;
//repository.save(new Ticket(0, "NCC#2017022418 two-step verification", "tarek@ati.tn", null, d, null));
return "Done";
}

/////////////////////////////Delete////////////////////////////////////

@RequestMapping("/ticket/delete")
public String deleteById(@RequestParam("id") long ticket) {
repository.delete(ticket);
return "done";

}

/////////////////////////////Update////////////////////////////////////

@RequestMapping("/ticket/update")
public String update(@RequestParam("id") long ticket) {
Date d =new Date();	
Ticket t = repository.findOne(ticket);
t.setOpened(d);
repository.save(t);
return "done";
}	

/////////////////////////////Findall////////////////////////////////////

@RequestMapping("/ticket/findall")
public String findAll(){
String result = "";

for(Ticket ticket : repository.findAll()){
result += ticket.toString() + "<br>";
}	

return result;	
}

/////////////////////////////findbyid////////////////////////////////////

@RequestMapping("/ticket/findbyid")
public String findById(@RequestParam("id") long ticket){
String result = "";
result = repository.findOne(ticket).toString();
return result;
}

@RequestMapping(value="/ticket/ajouter", method = RequestMethod.GET)
public ModelAndView ajouterTicket(){
	ModelAndView modelAndView = new ModelAndView();
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	User user = userservice.findUserByEmail(auth.getName());
	modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
	Ticket ticket = new Ticket();
	modelAndView.addObject("ticket", ticket);
	modelAndView.setViewName("technical/ajoutReclamation");
	return modelAndView;
}

@RequestMapping(value="/ticket/ajouter" , method=RequestMethod.POST)
public ModelAndView AjouterTicket(@Valid Ticket ticket, BindingResult bindingResult,HttpServletRequest request){
	
	  ModelAndView modelandview = new ModelAndView() ;
	  Principal principal = request.getUserPrincipal();
      String name = principal.getName();
      User user =  userrepository.findByEmail(name)  ;
	  Long id = user.getId();
	  String email = user.getEmail();
	  ticket.setUser(user);
	  ticket.setTitle(ticket.getTitle());
	  ticket.setSubjet(ticket.getSubjet());
	  ticket.setFrom(email);
	  repository.save(ticket)	;
	  modelandview.addObject("successMessage", "Votre réclamation a été enregistrée");

	  modelandview.setViewName("technical/ajoutReclamation");
	return modelandview ;
}


@RequestMapping(value="/ticket/repondre/{id}" , method=RequestMethod.GET)
@ResponseBody
public ModelAndView Repoticket(@PathVariable("id") Long id,@ModelAttribute("ticket") Ticket ticket , RedirectAttributes redir,HttpServletRequest req){
	
	  ModelAndView modelandview = new ModelAndView() ;
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userservice.findUserByEmail(auth.getName());
		modelandview.addObject("userName", user.getName() + " " + user.getLastName());
	  Ticket   tick= repository.findOne((long)id);
	  req.getSession().setAttribute("id" ,id);
	   req.setAttribute("id", (Long)id);
	   redir.addFlashAttribute("id",(Long)id);
	   System.out.println(id.toString());

	  modelandview.setViewName("admin/ajoutRecla");

	return modelandview ;
}

@RequestMapping(value="/ticket/repondre" , method=RequestMethod.POST)
@ResponseBody
public ModelAndView RepondreTicket(@ModelAttribute("ticket") Ticket ticket,
		HttpServletRequest req){
	  ModelAndView modelandview = new ModelAndView() ;
 
	try {

	 Long v=	(Long)req.getSession().getAttribute("id");
	// DateFormat formater ;
    // formater = new SimpleDateFormat();	  
     	Date aujourdhui = new Date() ;
    //	aujourdhui = formater.parse("dd-MM-yy");
      Ticket   tick1= repository.findOne((long)v);
	  System.out.println(tick1.toString());


	  tick1.setResponse(ticket.getResponse());
	  tick1.setOpened(aujourdhui);
	  repository.save(tick1)	;
	  List<Ticket> Listticket = (List<Ticket>) repository.findAll();
		modelandview.addObject("Listticket",Listticket);
	 
	  modelandview.setViewName("admin/Listticket");
	  }catch (Exception e) {
		  System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJ");
	}
	return modelandview ;
}
@RequestMapping(value="/ticket/afficher" , method=RequestMethod.GET)
@ModelAttribute("Listticket")
public ModelAndView ListTicket(){
	ModelAndView modelAndView = new ModelAndView();
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userservice.findUserByEmail(auth.getName());
			modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
			String Response = null ;
	List<Ticket> Listticket = (List<Ticket>) repository.findAll();
	modelAndView.addObject("Listticket",Listticket);
	modelAndView.setViewName("admin/Listticket");

	
	
	return modelAndView;
	
	
	
}	
@RequestMapping(value="/ticket/traitée" , method=RequestMethod.GET)
@ModelAttribute("Listticket")
public ModelAndView ListTickettraitée(@ModelAttribute("ticket") Ticket ticket){
	ModelAndView modelAndView = new ModelAndView();
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userservice.findUserByEmail(auth.getName());
			modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
			
		 
	List<Ticket> Listticket = (List<Ticket>) repository.findAll();
	modelAndView.addObject("Listticket",Listticket);
	modelAndView.setViewName("admin/Listticket");

	
	
	return modelAndView;
	
	
	
}	
	

	
	
}
