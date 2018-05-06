package com.info.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.info.model.Ticket;
import com.info.model.User;
import com.info.repo.InvoiceRepository;
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
	List<Invoice> Listinvoice = (List<Invoice>) repository.findAll();
	modelAndView.addObject("Listinvoice",Listinvoice);
	modelAndView.setViewName("admin/Listinvoice");
	
	
	return modelAndView ;
}
@RequestMapping(value="/invoice/{invoiceNumber}", method = RequestMethod.GET)
public ModelAndView ListInvoice(@PathVariable Long invoiceNumber,@ModelAttribute("invoice") Invoice invoice){
	ModelAndView modelAndView =new ModelAndView() ;
	Invoice inv = repository.findOne(invoiceNumber);
	System.out.println(inv.toString());
	inv.setStatus("paid");
	repository.save(inv);
	
	modelAndView.setViewName("financier/ListinvoiceOrg");
	
	
	return modelAndView ;
}
@RequestMapping(value="/Myinvoice/afficher", method = RequestMethod.GET)
public ModelAndView MyInvoice(HttpServletRequest request){
	ModelAndView modelAndView =new ModelAndView() ;
	  Principal principal = request.getUserPrincipal();
      String email = principal.getName();
      User user =  userrepo.findByEmail(email)  ;
      String org = user.getOrganisation();

	List<Invoice> Listinvoice = (List<Invoice>) repository.findbyadress(org);
	modelAndView.addObject("Listinvoice",Listinvoice);
	modelAndView.setViewName("financier/ListinvoiceOrg");
	
	
	return modelAndView ;
}
@Autowired
PdfGenaratorUtil pdfGenaratorUtil;

@RequestMapping(value = "/pdf/{invoiceNumber}", method = RequestMethod.GET)
@ResponseBody
public ModelAndView pdfInvoice(@PathVariable Long invoiceNumber,@ModelAttribute("invoice") Invoice invoice) throws Exception {
	ModelAndView modelAndView = new ModelAndView();
	 Map<String,String> data = new HashMap<String,String>();

		Invoice inv = repository.findOne(invoiceNumber);

	    data.put("Organisation",inv.getOrganisation());
	    data.put("Status",inv.getStatus());
	    data.put("Date",inv.getIssue_date().toString());


double total = inv.getTotal_amount();
String total2 = String.valueOf(total);
	    data.put("MontantTotal",total2);


	    pdfGenaratorUtil.createPdf("pdf",data); 
	    modelAndView.addObject("Organisation" ,data);
	    modelAndView.addObject("Status" ,data);
	    modelAndView.addObject("Date" ,data);
	    modelAndView.addObject("MontantTotal" ,data);

	modelAndView.setViewName("pdf");
	return modelAndView;  
}



}