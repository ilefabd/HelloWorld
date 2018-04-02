package com.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.info.model.User;
import com.info.repo.UserRepository;

@Controller
public class UserController {

	
	
	@Autowired
	UserRepository userrepo ;
	
	
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
	public ModelAndView deleteById(@PathVariable("id") long id) {
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
	public ModelAndView ActiverUser(@PathVariable("id") long id) {
		ModelAndView modelAndView = new ModelAndView();
    	List<User> userlist = (List<User>) userrepo.findAll();
		modelAndView.addObject("userlist",userlist);
            User user = userrepo.findOne((long) id);
             user.setActive(1);
             userrepo.save(user);
		modelAndView.setViewName("admin/listuser");

	return modelAndView;

	}
	
}
