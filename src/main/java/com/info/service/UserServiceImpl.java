package com.info.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.model.DemandeEnCours;
import com.info.model.Prefix;
import com.info.model.Response;
import com.info.model.Role;
import com.info.model.Technologie;
import com.info.model.User;
import com.info.repo.DemandeEnCoursRepository;
import com.info.repo.PrefixRepository;
import com.info.repo.ResponseRepository;
import com.info.repo.RoleRepository;
import com.info.repo.TechnologieRepository;
import com.info.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private DemandeEnCoursRepository demanderepo;
	@Autowired
	TechnologieRepository techrepository ;
	@Autowired
	private PrefixRepository prefixrepository ;
	@Autowired
	private ResponseRepository responserepository ;
	

	
	
	//find user by email 
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	//save ADMIN user 
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	

	//save Financier user 
	@Override
	public void saveFinacier(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("Financier");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);		
	}

	//save demande 
	@Override
	public void savedemande(DemandeEnCours demande ,HttpServletRequest request) {
			//get user after authentication 
	        Principal principal = request.getUserPrincipal();
	        String email = principal.getName();
	        User user =  userRepository.findByEmail(email)  ;
            String org = user.getOrganisation();
		    //select prefix
            Prefix p = prefixrepository.findById((long) 1); 
            //select technologie
		    Technologie t = techrepository.findById((long)7);
			//add demmand
		    demande.setPrefix(p.getPrefix());
			demande.setTechnologie(t.getTechnologie());
			demande.setDate(new Date());
			demande.setPrefi(p)	;
			demande.setTech(t);
			demande.setOrganisation(org);
			demande.setStatus("EN COURS");
			demande.setEmail(email);
			//save demand
			demanderepo.save(demande);
	}

	
	//add response for demand 
	@Override
	public void repondredemande(Response response) {
		//get demand by id 
      DemandeEnCours d = demanderepo.findOne((long) 27); 
      //set status of demand
      d.setStatus("demande traitée");
      //add response
      response.setId_demande(d.getId());
      response.setOrganisation(d.getOrganisation());
      response.setResponse("votre demande est acceptée");
      //save reqponse
     responserepository.save(response);
      
	}

	@Override
	public void saveTechnical(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("technicien");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);		
	}
	
	

}