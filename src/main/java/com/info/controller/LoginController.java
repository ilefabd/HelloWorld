package com.info.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.DemandeEnCours;
import com.info.model.User;
import com.info.repo.UserRepository;
import com.info.service.UserService;



@RestController
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepository ;
	//--------------------------------Login View----------------------------------------//

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
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
	public ModelAndView registrationFinancier(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registrationF");
		return modelAndView;
	}
	
	
	
	
	@RequestMapping(value = "/registration/Financier", method = RequestMethod.POST)
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
			modelAndView.addObject("successMessage", "Financier has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registrationF");
			
		}
		return modelAndView;
	}
	@RequestMapping(value="/registration/technical", method = RequestMethod.GET)
	public ModelAndView registrationTechnical(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registrationT");
		return modelAndView;
	}
	
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
			modelAndView.addObject("successMessage", "Technical has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registrationT");
			
		}
		return modelAndView;
	}
	//-------------------------------- admin home view ----------------------------------------//

	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		System.out.println(user.getRoles().toString());
		return modelAndView;
	}
	
	//-------------------------------- financier home view ----------------------------------------//

	@RequestMapping(value="/financier/home", method = RequestMethod.GET)
	public ModelAndView homef(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("finanMessage","Content Available Only for Users with fin Role");
		modelAndView.setViewName("financier/home");
		return modelAndView;
	}
	//-------------------------------- technical home view ----------------------------------------//

		@RequestMapping(value="/technical/home", method = RequestMethod.GET)
		public ModelAndView homeT(){
			ModelAndView modelAndView = new ModelAndView();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
			modelAndView.addObject("techniMessage","Content Available Only for Users with technical Role");
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
	
	
	
	}		
        		
        		