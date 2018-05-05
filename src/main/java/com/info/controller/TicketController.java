package com.info.controller;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.info.PdfGenaratorUtil;
import com.info.model.Ipv4range;
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


@RequestMapping(value="/ticket/repondre/{Ticket}" , method=RequestMethod.GET)
@ResponseBody
public ModelAndView Repoticket(@ModelAttribute("ticket") Ticket ticket ,@PathVariable("Ticket") Long Ticket,HttpServletRequest req,RedirectAttributes redir){
	
	  ModelAndView modelandview = new ModelAndView() ;
	
	  Ticket   tick= repository.findOne((long)Ticket);
	
       req.getSession().setAttribute("Ticket" ,Ticket);
	   req.setAttribute("Ticket", Ticket);
	  modelandview.setViewName("admin/ajoutRecla");

	return modelandview ;
}
@RequestMapping(value="/ticket/repondre/{Ticket}" , method=RequestMethod.POST)
@ResponseBody
public ModelAndView RepondreTicket(@ModelAttribute("ticket") Ticket ticket,@PathVariable("Ticket") Long Ticket
		,HttpServletRequest req){
	  
	  ModelAndView modelandview = new ModelAndView() ;
	  System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJ");

	  Long v=	(Long)req.getSession().getAttribute("Ticket");

	  Ticket   tick1= repository.findOne((long)v);

	 // Ticket   tick= repository.findOne((long)Ticket);
	  System.out.println(tick1.toString());

	  tick1.setResponse(ticket.getResponse());
	  repository.save(tick1)	;
	  modelandview.addObject("ticket", new Ticket());
	  List<Ticket> Listticket = (List<Ticket>) repository.findAll();
		modelandview.addObject("Listticket",Listticket);
	 
	  modelandview.setViewName("admin/Listticket");
	return modelandview ;
}
@RequestMapping(value="/ticket/afficher" , method=RequestMethod.GET)
@ModelAttribute("Listticket")
public ModelAndView ListTicket(){
	ModelAndView modelAndView = new ModelAndView();

	List<Ticket> Listticket = (List<Ticket>) repository.findAll();
	modelAndView.addObject("Listticket",Listticket);
	modelAndView.setViewName("admin/Listticket");

	
	
	return modelAndView;
	
	
	
}	
@Autowired
PdfGenaratorUtil pdfGenaratorUtil;
@RequestMapping(value = "/pdf", method = RequestMethod.GET)
@ResponseBody
public ModelAndView pdf() throws Exception {
	ModelAndView modelAndView = new ModelAndView();
	 Map<String,String> data = new HashMap<String,String>();
	    data.put("name","ilef");
	    data.put("prenom","Abd");
	    pdfGenaratorUtil.createPdf("pdf",data); 
	    modelAndView.addObject("name" ,data);
	    modelAndView.addObject("prenom" ,data);

	modelAndView.setViewName("pdf");
	return modelAndView;  
}
	
	
	
	
	
	
}
