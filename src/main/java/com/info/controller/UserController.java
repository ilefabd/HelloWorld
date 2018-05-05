package com.info.controller;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.DemandeEnCours;
import com.info.model.User;
import com.info.repo.DemandeEnCoursRepository;
import com.info.repo.UserRepository;
import com.info.service.UserService;

@Controller
public class UserController {

	
	@Autowired 
	DemandeEnCoursRepository demanderepo;
	@Autowired
	UserRepository userrepo ;
	@Autowired
	UserService userservice ;
	
	//afficher la liste des utilistateurs
	@RequestMapping(value="/user/listofuser", method = RequestMethod.GET )
    @ModelAttribute("userlist")
	public ModelAndView listofuser(){
		ModelAndView modelAndView = new ModelAndView();
    	List<User> userlist = (List<User>) userrepo.findAll();

		modelAndView.addObject("userlist",userlist);
		modelAndView.setViewName("admin/listuser");

		return modelAndView;
	}
	//Desactiver le compte  d'un utilisateur
	@RequestMapping("/user/delete/{id}")
	public ModelAndView DesablePersonAccount(@PathVariable("id") long id) {
		ModelAndView modelAndView = new ModelAndView();
    	List<User> userlist = (List<User>) userrepo.findAll();
		modelAndView.addObject("userlist",userlist);
            User user = userrepo.findOne((long) id);
             user.setActive(0);
             userrepo.save(user);
		modelAndView.setViewName("admin/listuser");

	return modelAndView;

	}
	//Activer le compte d'un utilisateur
	@RequestMapping("/user/activer/{id}")
	public ModelAndView ActivatePersonAccount(@PathVariable("id") long id) {
		ModelAndView modelAndView = new ModelAndView();
    	List<User> userlist = (List<User>) userrepo.findAll();
		modelAndView.addObject("userlist",userlist);
            User user = userrepo.findOne((long) id);
             user.setActive(1);
             userrepo.save(user);
		modelAndView.setViewName("admin/listuser");

	return modelAndView;

	}
	@Autowired
	
	    private JavaMailSender sender;
	
	 
	
	    @RequestMapping(value="/simpleemail/{id}")
	    @ModelAttribute("Listdemande")
	    @ResponseBody
	
	    public ModelAndView  home(@PathVariable Long id,@ModelAttribute("demande") DemandeEnCours demande) {
    		ModelAndView modelAndView = new ModelAndView();

	        try {

		    	DemandeEnCours d= demanderepo.findOne((long)id); 
		    	//System.out.println(d.toString());
	            userservice.sendEmail(d);
	            d.setStatus("traitée");
	            demanderepo.save(d);
	            System.out.println("Email Sent!");
	    		modelAndView.addObject("successMessage","Email Sent!");

	            modelAndView.setViewName("admin/mail");


	        }catch(Exception ex) {
	
	        	System.out.println( "Error in sending email: "+ex);
	
	        }
        	return modelAndView;

	    }

	 

	 
	
}
