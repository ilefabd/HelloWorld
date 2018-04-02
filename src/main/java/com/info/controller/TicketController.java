package com.info.controller;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.Response;
import com.info.model.Ticket;
import com.info.model.User;
import com.info.repo.TicketRepository;
import com.info.repo.UserRepository;


@RestController
public class TicketController  {

	@Autowired
	TicketRepository repository ;
	
	@Autowired
	UserRepository userrepository ;
/////////////////////////////Save////////////////////////////////////

	
@RequestMapping("/ticket/save")
public String process() {



Date d = new Date() ;
repository.save(new Ticket("NCC#2017022418 two-step verification", "tarek@ati.tn", d));
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
	  ticket.setSubjet(ticket.getSubjet());
	  ticket.setFrom(email);
	  repository.save(ticket)	;
	  
	  modelandview.setViewName("technical/ajoutReclamation");
	return modelandview ;
}

@RequestMapping(value="/ticket/afficher" , method=RequestMethod.GET)
@ModelAttribute("Listticket")
public ModelAndView resourcesnondispo(){
	ModelAndView modelAndView = new ModelAndView();

	List<Ticket> Listticket = (List<Ticket>) repository.findAll();
	modelAndView.addObject("Listticket",Listticket);
	modelAndView.setViewName("admin/Listticket");

	
	
	return modelAndView;
	
	
	
}	
	
	
	
	
	
	
}
