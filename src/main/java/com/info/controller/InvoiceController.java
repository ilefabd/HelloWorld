package com.info.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.info.PdfGenaratorUtil;
import com.info.model.DemandeEnCours;
import com.info.model.Invoice;
import com.info.model.Organisation;
import com.info.model.Prefix;
import com.info.model.Technologie;
import com.info.model.Ticket;
import com.info.model.User;
import com.info.repo.InvoiceRepository;
import com.info.repo.OrganisationRepository;
import com.info.repo.UserRepository;
import com.info.service.UserService;


@RestController
public class InvoiceController {
	@Autowired
	InvoiceRepository repository ;
	@Autowired
UserService userservice ;
	@Autowired
	UserRepository userrepo ;
	@Autowired
	OrganisationRepository orgrepo ;
	
/////////////////////////////Save////////////////////////////////////

	
	@RequestMapping("/invoice/save")
	public String process() {
	
		
			
                   Date d = new Date() ;
                   //repository.save(new Invoice("paid", 1555.22 , d))	;		     		
			
		return "Done";
	}
	
/////////////////////////////Delete////////////////////////////////////
	
@RequestMapping("/invoice/delete")
public String deleteById(@RequestParam("id") long invoiceNumber) {
repository.delete(invoiceNumber);
return "done";

}
	
/////////////////////////////Update////////////////////////////////////

@RequestMapping("/invoice/update")
public String update(@RequestParam("id") long invoiceNumber) {
Date d =new Date();	
Invoice invoice = repository.findOne(invoiceNumber);
invoice.setStatus("En Cours");
repository.save(invoice);
return "done";
}	
	
/////////////////////////////Findall////////////////////////////////////

@RequestMapping("/invoice/findall")
public String findAll(){
String result = "";

for(Invoice invoice : repository.findAll()){
 result += invoice.toString() + "<br>";
}	
	
return result;	
}

/////////////////////////////findbyid////////////////////////////////////

@RequestMapping("/invoice/findbyid")
public String findById(@RequestParam("id") long invoiceNumber){
String result = "";
result = repository.findOne(invoiceNumber).toString();
return result;
}

@RequestMapping(value="/invoice/add", method = RequestMethod.GET)
public String AddInvoice(){
     Invoice invoice =new Invoice();
  // userservice.saveInvoice(invoice);
	
	return "done";
}
@RequestMapping(value="/invoice/afficher", method = RequestMethod.GET)
public ModelAndView ListInvoice(){
	ModelAndView modelAndView =new ModelAndView() ;
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	User user = userservice.findUserByEmail(auth.getName());
	modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
	String S = "payée";
	List<Invoice> Listinvoice = (List<Invoice>) repository.findByStatus(S);
	modelAndView.addObject("Listinvoice",Listinvoice);
	modelAndView.setViewName("admin/Listinvoice");
	
	
	return modelAndView ;
}
@RequestMapping(value="/invoice/{invoiceNumber}", method = RequestMethod.GET)
public ModelAndView ListInvoice(@PathVariable Long invoiceNumber,@ModelAttribute("invoice") Invoice invoice){
	ModelAndView modelAndView =new ModelAndView() ;
	Invoice inv = repository.findOne(invoiceNumber);
	System.out.println(inv.toString());
	inv.setStatus("payée");
	repository.save(inv);
	List<Invoice> Listinvoice = (List<Invoice>) repository.findAll();
	modelAndView.addObject("Listinvoice",Listinvoice);
	modelAndView.setViewName("financier/ListinvoiceOrg");
	
	
	return modelAndView ;
}
@RequestMapping(value="/Myinvoice/afficher", method = RequestMethod.GET)
public ModelAndView MyInvoice(HttpServletRequest request){
	ModelAndView modelAndView =new ModelAndView() ;
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	User user = userservice.findUserByEmail(auth.getName());
	modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
	  Principal principal = request.getUserPrincipal();
      String email = principal.getName();
      User user1 =  userrepo.findByEmail(email)  ;
      String org = user1.getOrganisation();

	List<Invoice> Listinvoice = (List<Invoice>) repository.findbyadress(org);
	modelAndView.addObject("Listinvoice",Listinvoice);
	modelAndView.setViewName("financier/ListinvoiceOrg");
	
	
	return modelAndView ;
}
@Autowired
PdfGenaratorUtil pdfGenaratorUtil;

@RequestMapping(value = "/pdf/{invoiceNumber}", method = RequestMethod.GET)
@ResponseBody
public ModelAndView pdfInvoice(@PathVariable Long invoiceNumber,@ModelAttribute("invoice") Invoice invoice,HttpServletRequest request) throws Exception {
	ModelAndView modelAndView = new ModelAndView();
	
	 Map<String,String> data = new HashMap<String,String>();

		Invoice inv = repository.findOne(invoiceNumber);
		 Principal principal = request.getUserPrincipal();
	      String email = principal.getName();
	      User user =  userrepo.findByEmail(email)  ;
	      String org = user.getOrganisation();
	      Organisation o = orgrepo.findByOrgname(org);
	      double total = inv.getTotal_amount();
		    String total2 = String.valueOf(total);
		    double tax=(inv.getTotal_amount()*19)/100 ;
		    String taxe =String.valueOf(tax);
		    double globaltotal =tax + total ; 
		    String glob =String.valueOf(globaltotal);  
		    String id =String.valueOf(invoiceNumber);  
		    String iduser =String.valueOf(user.getId());  

	            
	    data.put("idinv",id);
	    data.put("detail",inv.getDetail());
	    data.put("iduser",iduser);

		data.put("email",o.getEmail());
		data.put("adress",o.getAddress());

	    data.put("Organisation",inv.getOrganisation());
	    data.put("Status",inv.getStatus());
	    SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
	    String mdy = mdyFormat.format(inv.getIssue_date());
	    data.put("Date",mdy);


	   

	    data.put("MontantTotal",total2);

	    data.put("taxe",taxe);
	    data.put("glob",glob);


	    modelAndView.addObject("idinv" ,id);
	    modelAndView.addObject("iduser" ,iduser);
	    modelAndView.addObject("detail" ,inv.getDetail());

	    modelAndView.addObject("email" ,o.getEmail());
	    modelAndView.addObject("adress" ,o.getAddress());

	    modelAndView.addObject("Organisation" ,org);
	    modelAndView.addObject("Date" ,mdy);
	    modelAndView.addObject("MontantTotal" ,total2);
	    modelAndView.addObject("taxe" ,taxe);
	    modelAndView.addObject("glob" ,glob);
	    pdfGenaratorUtil.createPdf("pdf",data,inv.getInvoiceNumber());

	modelAndView.setViewName("pdf");
	return modelAndView;  
}

//Demande View 
		@RequestMapping(value="/invoice/ajouter", method = RequestMethod.GET )
	    @ModelAttribute("Orglist")
		public ModelAndView demandeResource(){
			ModelAndView modelAndView = new ModelAndView();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userservice.findUserByEmail(auth.getName());
			modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
	    	List<Organisation> Orglist = (List<Organisation>) orgrepo.findAll();
	    	Invoice invoice = new Invoice() ;
			modelAndView.addObject("invoice", invoice);
			modelAndView.addObject("Orglist",Orglist);

			modelAndView.setViewName("admin/ajoutfacture");
			return modelAndView;
		}
		// create new demande
				@RequestMapping(value = "/invoice/ajouter", method = RequestMethod.POST)
				public ModelAndView createNewdemande(@Valid Invoice invoice , BindingResult bindingResult , HttpServletRequest request) throws ParseException {
					ModelAndView modelAndView = new ModelAndView();
					Date date = new Date();
				    SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
				    String mdy = mdyFormat.format(date);

					invoice.setIssue_date(mdyFormat.parse(mdy));
					invoice.setStatus("Non payée");
					repository.save(invoice);
					modelAndView.addObject("successMessage", "Votre facture a eté ajoutée avec succés");
					modelAndView.addObject("invoice", new Invoice());
					modelAndView.setViewName("admin/Listinvoice");
					
				
				return modelAndView;
			}
				
				
				
				@RequestMapping("/invoice/unpaid")
			    @ModelAttribute("Listinvoice")
				public ModelAndView findbyStatus(String Status){
					ModelAndView modelAndView = new ModelAndView();
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					User user = userservice.findUserByEmail(auth.getName());
					modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
					String S = "Non payée";
					List<Invoice> Listinvoice = (List<Invoice>) repository.findByStatus(S);
					modelAndView.addObject("Listinvoice",Listinvoice);
					modelAndView.setViewName("admin/Listinvoice");

					
					
					return modelAndView;
					
					
					
				}
}