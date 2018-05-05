package com.info.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.info.ip.Ipv4;
import com.info.ip.Ipv4Range;
import com.info.ip.PrefixFinder.Strategy;
import com.info.model.Customer;
import com.info.model.DemandeEnCours;
import com.info.model.Ipv4range;
import com.info.model.Prefix;
import com.info.model.Response;
import com.info.model.User;
import com.info.repo.Ipv4rangeRepository;
import com.info.repo.ResponseRepository;
import com.info.service.UserService; 

@RestController
public class Ipv4RangeController {

	@Autowired
	Ipv4rangeRepository ipv4repository;
	@Autowired
	Ipv4rangeRepository ipvrepository;
	@Autowired
	ResponseRepository  responserepository ;

	@Autowired
	UserService userservice ;
	
	
	
	
	
// save an  ip4range 

	@RequestMapping("/ipv4/save")
	public String process(){
		
		
		Ipv4 start = Ipv4.of("192.168.0.0");
		Ipv4 end = Ipv4.of("192.168.0.255");
		

		//-----------------------------------/16---------------------
				Ipv4Range e = Ipv4Range.parse("192.168.0.0/16");
				//-----------------------------------/18---------------------
				Ipv4Range f = Ipv4Range.parseCidr("192.168.0.0/17");
				Ipv4Range g =Ipv4Range.parse("192.168.64.0/18");
				Ipv4Range h =Ipv4Range.parse("192.168.128.0/18");
			    Ipv4Range test =Ipv4Range.parse("192.168.192.0/18");
			    String stri=f.toStringInCidrNotation();
		ipv4repository.save(new Ipv4range(e));
		return "Done";
	}
	
	// find ip4range list
	@RequestMapping("/ipv4/findall")
	public String findAll(){
		String result = "";
		
		for(Ipv4range ipv4 : ipv4repository.findAll()){
			result += ipv4.toString() + "<br>";
		}
		
		return result;
	}
	
	 //delete ip4range list 
		@RequestMapping("/ipv4/delete")
		public String deleteById() {
			String result ="";
			ipv4repository.deleteAll();
			return result="done";
			
		}
	
	//Find ip4range by id 
		@RequestMapping("/ipv4/findbyid")
		public Ipv4range findById(@RequestParam("id") Long id){
			Ipv4range ip = ipv4repository.findOne(id);
			return ip;
		}
		//Find ip4range by id 
				@RequestMapping("/ipbyRange")
				public Ipv4range findByIp(String iprange){
					Ipv4range ip = ipv4repository.findByrange(iprange);
					return ip;
				}
		
	//-----------------------------------------View -----------------------------------------------------
		//DeleteView
		@RequestMapping("/ip/delete/{id}")
		public ModelAndView deleteById(@PathVariable("id") long id) {
			ModelAndView modelAndView = new ModelAndView();
	    	List<Ipv4range> iplist = (List<Ipv4range>) ipv4repository.findAll();
			modelAndView.addObject("iplist",iplist);
	            Ipv4range ip = ipv4repository.findOne((long) id);
	        ipv4repository.delete(ip);
			modelAndView.setViewName("ipvrange2");

		return modelAndView;
		}
		
		//AjoutView
		@RequestMapping(value="/ip/ajouter", method = RequestMethod.GET)
		public ModelAndView registration(){
			ModelAndView modelAndView = new ModelAndView();
			Ipv4range ip =new Ipv4range() ;
			modelAndView.addObject("ip", ip);
			modelAndView.setViewName("admin/ajoutip");
			return modelAndView;
		}
		
		@RequestMapping(value="/ip/ajouter" , method = RequestMethod.POST)
		public ModelAndView SaveIp(@Valid Ipv4range ip, BindingResult bindingResult) {
			ModelAndView modelAndView = new ModelAndView();
			try {
	
				 Ipv4range iprange = userservice.findByrange(ip.getRange());
		            System.out.println(iprange.toString());
            if(iprange != null) {
            	
            	bindingResult
				.rejectValue("range" ,"error.ip",
						"ip already used");
            	modelAndView.addObject("failMessage", "*ip already used");
    			modelAndView.addObject("ip", new Ipv4range());

    			modelAndView.setViewName("admin/ajoutip");
                System.out.println("used adress");

            	
            } 
            }
            
            catch  (Exception e){
            	try {ip.setIprange(Ipv4Range.parse(ip.getRange()));

        	System.out.println("ip n'existe pas");
        
		ipv4repository.save(ip);
		modelAndView.addObject("successMessage", "Ip range has been registered successfully");
		modelAndView.addObject("ip", new Ipv4range());

		modelAndView.setViewName("admin/ajoutip");
		}  catch (Exception ei){

	    			modelAndView.addObject("failMessage", "*please enter a correct ip ex :x.x.x.x/[] ");
	    			modelAndView.addObject("ip", new Ipv4range());

	    			modelAndView.setViewName("admin/ajoutip");
				}

            } 
    			
            
           
            
           
            	
            	
            	

            
			
		return modelAndView;

		}
		
		//Afficher la liste des adresse IP View 
		@RequestMapping(value = "/iprangelist", method = RequestMethod.GET)
	    @ResponseBody
	    public ModelAndView Iplist() {
		   ModelAndView modelAndView = new ModelAndView();
		   List<Ipv4range> Listip = (List<Ipv4range>) ipvrepository.findAll();
			modelAndView.addObject("Listip", Listip);
		     
		    modelAndView.setViewName("ipvrange2");
			return modelAndView;
}
		//Afficher la liste des adresse IP affectées View

		@RequestMapping("/resourcesnondispo")
	    @ModelAttribute("Listdemande")
		public ModelAndView resourcesnondispo(){
			ModelAndView modelAndView = new ModelAndView();

			List<Response> Listresponse = (List<Response>) responserepository.findAll();
			modelAndView.addObject("Listresponse",Listresponse);
			modelAndView.setViewName("admin/Listresponse");

			
			
			return modelAndView;
	
		}
		
		
		@Autowired
		ResponseRepository responserepo;
		
		@SuppressWarnings("unused")
		@RequestMapping(value = "/whois" , method=RequestMethod.GET)
		
		public ModelAndView whois(@RequestParam("id") String id ){
		 ModelAndView modelandview = new ModelAndView();
	       System.out.println(id.toString());


	   
	       try {
	    	      Ipv4Range  ip =Ipv4Range.parse(id);
	    	       System.out.println(ip.toString());
			       
			       if (ip!=null)
			       		{
							 Response res = responserepo.findbyadress(id)    ;
						       System.out.println(res.getOrganisation());
							   
						       System.out.println("ip valide");
						       
						       modelandview.addObject("res", res);
						       modelandview.addObject("org", res.getOrganisation());
						       modelandview.addObject("response", res.getResponse());

						       modelandview.setViewName("admin/whois");
							   
			       		} }
			       
	    	  
	    	  catch (Exception e) {
		    	   System.out.println("ip non valide");

	    		  ModelAndView mode = new ModelAndView();
	  			  mode.addObject("successMessage", "Votre demande est ajoutée avec succés");
	  			  mode.setViewName("default");
	  			  return mode ;
					}
	    	  
	    
	        return modelandview;
		
		 
		}   
}
