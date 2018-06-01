 package com.info.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.info.ip.Ipv4;
import com.info.ip.Ipv4Range;
import com.info.model.DemandeEnCours;
import com.info.model.Invoice;
import com.info.model.Ipv4range;
import com.info.model.Prefix;
import com.info.model.Response;
import com.info.model.Role;
import com.info.model.SurveyAnswerStatistics;
import com.info.model.Technologie;
import com.info.model.User;
import com.info.repo.DemandeEnCoursRepository;
import com.info.repo.InvoiceRepository;
import com.info.repo.Ipv4rangeRepository;
import com.info.repo.PrefixRepository;
import com.info.repo.ResponseRepository;
import com.info.repo.RoleRepository;
import com.info.repo.TechnologieRepository;
import com.info.repo.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	@Autowired
    private JavaMailSender sender;
@Autowired
Ipv4rangeRepository iprangerepository;
	@Autowired
	private Ipv4rangeRepository ipvrepository ;
	@Autowired
	private InvoiceRepository invoicerepository ;
	public Ipv4range findByrange(String range){
		Ipv4range ip = ipvrepository.findByrange(range);
		return ip;
	}
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
		  
           
		    demande.setPrefix(demande.getPrefix());
			demande.setTechnologie(demande.getTechnologie());
			demande.setDate(new Date());
			demande.setPrefi(demande.getPrefi())	;
			demande.setOrganisation(org);
			demande.setStatus("EN COURS");
			demande.setEmail(email);
			demande.setDescription(demande.getDescription());
			//save demand
			demanderepo.save(demande);
	}

	
	//add response for demand 
	@Override
	public void repondredemande(@ModelAttribute("iprange") Ipv4range ip4range,
			@ModelAttribute("demande") DemandeEnCours demande ,Response response,HttpServletRequest req) 
	{
		Invoice invoice = new Invoice() ;
		System.out.println("//////////////////////////test////////");
		
		Long v=	(Long)req.getSession().getAttribute("id");
	       //  System.out.println(v);
			 DemandeEnCours demand= demanderepo.findOne((long) v); 
		    //  System.out.println(d);

      Ipv4range ip=ipvrepository.findOne(ip4range.getId());
      System.out.println(ip.getIprange().toString());
      System.out.println(ip.toString());
	 
	  Ipv4Range range=ip.getIprange();
      Ipv4 i = range.start();
      
      Ipv4Range e = Ipv4Range.from(i).andPrefixLength(demand.getPrefix());
	  
      	  
      List<Ipv4Range> ipv4 = new ArrayList<Ipv4Range>();
	  
		ipv4=range.exclude(e);
		

		for (Ipv4Range ipv : ipv4) {
		  //  System.out.println(ipv.splitToPrefixes());
			  ip4range.setIprange(ipv);
			  String r= ipv.splitToPrefixes().toString();
			  System.out.println(r);
             ip4range.setRange(r);
		}
		  
	
	      demand.setStatus("traitée");
	      
          response.setId_demande(demand.getId());	
          response.setOrganisation(demand.getOrganisation());
	     
        response.setResponse(e.toString());
       response.setSize(e.size());
        responserepository.save(response);	

        saveInvoice(invoice, demand);
        //////////////////////////////////////////////////////////////////
    ipvrepository.save(ip4range);
	 
	}
	
	@Override
	public void saveTechnical(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("technicien");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);		
	}
	
	@Override
	public void saveInvoice(Invoice invoice,DemandeEnCours demand) {
		Date d = new Date();
		String prefix = demand.getPrefix();
		System.out.println(prefix);
		int p = Integer.parseInt(prefix);
if (p>=24 )
{		invoice.setTotal_amount(2500);
}
if (p==23 )
{		invoice.setTotal_amount(2700);
}
if (p==22 )
{		invoice.setTotal_amount(3000);
}
if (p==20 || p==19)
{		invoice.setTotal_amount(5000);
}if (p==18 || p==17)
{		invoice.setTotal_amount(7500);
}
if (p==16 || p==15)
{		invoice.setTotal_amount(9000);
}if (p==14 || p==13)
{		invoice.setTotal_amount(10000);
}if (p==12 || p==11 )
{		invoice.setTotal_amount(12000);
}

		invoice.setStatus("Non payée");
		invoice.setIssue_date(d);
		invoice.setDetail(" consomation ressources IP suite à la demande du bloc / "+demand.getPrefix());
	//	invoice.setTotal_amount(800);
		String org=demand.getOrganisation();
		String email = demand.getEmail();
        User user =  userRepository.findByEmail(email)  ;
        System.out.println(user.toString());
        invoice.setUser(user);
        invoice.setOrganisation(org);;
		invoicerepository.save(invoice);

		
	}

	public List<Response> list() {

	return null ;
		
	}
	
	   public void sendEmail(DemandeEnCours demand) throws Exception{
	        MimeMessage message = sender.createMimeMessage();
	
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        helper.setTo(demand.getEmail());
	
	        helper.setText("Votre demande est refusée");
	
	        helper.setSubject("ATI");
	
	         
	
	        sender.send(message);
	
	    }

	
	   public void sendEmail2(DemandeEnCours d ,Response response) {
	        MimeMessage message = sender.createMimeMessage();
	
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        try {
				helper.setTo(d.getEmail());
				 helper.setText("Le bloc41.224.0.0/18 vous a été attribué");

			        helper.setSubject("ATI");
			
			         
			
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	       
	        sender.send(message);
	
	    }
	   public void stat() {
			ModelAndView modelAndView =new ModelAndView() ;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
		
//			   //    List<SurveyAnswerStatistics> result = responserepository.fi
//			       Integer ipalloue =responserepository.findResponseCount();
//			       Integer ipdisponible =iprangerepository.findiprangeCount();
//
//			       Long y = null;
//			       String x = "" ;
//			       List<Integer> inshoreSales =  new ArrayList<>() ;
//		            List<String> organisation =  new ArrayList<>();
//
//			       for (int i = 0 ; i<result.size();i++)
//			       {
//			    	   
//					     y = result.get(i).getCnt();
//				         x = result.get(i).getOrganisation();
//				         Integer count = (int) (long) y;
//			       
//	                     inshoreSales.add(count);
//	                     organisation.add(x);
//	              
//				         
//
//
//					     
//			       }
//			     System.out.println(ipalloue);
//			     System.out.println(ipdisponible);
//
//			      
//
//			       // model.addObject("ATI", '50%');
//			      //  model.addObject("midwestSales", ipdisponible);
//			     //   model.addObject("westSales", ipalloue);
//			       
//			      //Column chart
//			        modelAndView.addObject("x", organisation);
//	  
//			           // System.out.println(inshoreSales.toString());
//			        modelAndView.addObject("inshoreSales", inshoreSales);
//			        modelAndView.addObject("organisation", organisation);
//			          //  System.out.println(organisation.toString());


			        
	    }
}