package com.info.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.model.Invoice;
import com.info.repo.InvoiceRepository;

@RestController
public class InvoiceController {
	@Autowired
	InvoiceRepository repository ;
	
/////////////////////////////Save////////////////////////////////////

	
	@RequestMapping("/invoice/save")
	public String process() {
	
		
			
                   Date d = new Date() ;
                   repository.save(new Invoice("paid", 1555.22 , d))	;		     		
			
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





}