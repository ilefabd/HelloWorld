package com.info.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.info.ip.Ipv4Range;
import com.info.model.Ipv4range;
import com.info.model.Response;
import com.info.model.Statistic;
import com.info.model.SurveyAnswerStatistics;
import com.info.model.Ticket;
import com.info.model.User;
import com.info.repo.Ipv4rangeRepository;
import com.info.repo.ResponseRepository;
import com.info.repo.StatisticsRepository;
import com.info.service.UserService;

@RestController
public class StatisticsController {

	@Autowired
	StatisticsRepository repository ;
	@Autowired
	ResponseRepository responserepository ;
	@Autowired
	Ipv4rangeRepository iprangerepository ;
	@Autowired
	    JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/statistics", method=RequestMethod.GET)
	
		public ModelAndView RessourceSurvey(){
		ModelAndView model =new ModelAndView() ;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByEmail(auth.getName());
		  // List<SurveyAnswerStatistics> result = responserepository.findSurveyCount();
		   //    Integer ipdisponible =iprangerepository.findiprangeCount();
		     

		List<Response>listresp =(List<Response>) responserepository.findAll();
		      
	       List<Response> listrespParOrg=(List<Response>) responserepository.ressourcesParOrganisation();
       
		       
		       Long ipalloue =(long) 0;
		       for(Response r:listresp)
		       {
		    		Ipv4Range e = Ipv4Range.parse(r.getResponse());
		    	 Long  ip=  e.size();
		    	 ipalloue = ipalloue + ip ;

		    	   
		       }
		       
		       List<Ipv4range> listip=(List<Ipv4range>)iprangerepository.findAll(); 
			      Long ipdisponible =(long) 0;
			       for(Ipv4range r:listip)
			       {
			    	 Long  ip=  r.getIprange().size();
			    	 ipdisponible = ipdisponible + ip ;

			    	   
			       }
				  
		       
		       Long y = null;
		       String x = "" ;
		       List<Integer> inshoreSales =  new ArrayList<>() ;
	            List<String> organisation =  new ArrayList<>();

		       for (int i = 0 ; i<listrespParOrg.size();i++)
		       {

				     y = listrespParOrg.get(i).getSize();
			         x = listrespParOrg.get(i).getOrganisation();
			         Integer count = (int) (long) y;
		       
                     inshoreSales.add(count);
                     organisation.add(x);
              
			         


				     
		       }
	  System.out.println(organisation.toString());
	  System.out.println(ipdisponible);

		      //pie chart 
//		        float v = (float) 21.28 ;
//		        float v1 = (float) 17.38 ;
//		        float v2 = (float) 11.63 ;
//		        float v3 = (float) 10.10 ;
//		        float v4 = (float) 4.49 ;
//		        float autre = (float)10.12  ;
//		        float ATI = (float) 25 ;
//
//		        model.addObject("Ooredoo", v);
//		        model.addObject("Orange", v1);
//		        model.addObject("Topnet", v2);
//		        model.addObject("TunisieTelecom", v3);
//		        model.addObject("Globalnet", v4);
//		        model.addObject("autre", autre);
//		        model.addObject("ATI", ATI);

	       model.addObject("midwestSales", ipdisponible);
		    model.addObject("westSales", ipalloue);
		       
		      //Column chart
		           model.addObject("x", organisation);
  
		           // System.out.println(inshoreSales.toString());
			        model.addObject("inshoreSales", inshoreSales);
			        model.addObject("organisation", organisation);
		          //  System.out.println(organisation.toString());
					model.addObject("userName", user.getName() + " " + user.getLastName());
					model.addObject("adminMessage","ADMIN HOME");


		            model.setViewName("admin/statisticip");
		            return model ;

		     }
	
//----------------------------------TestCrud-------------------------------------------------------//	
/////////////////////////////save////////////////////////////////////

@RequestMapping("/statistics/save")
public String process() {

Date d = new Date() ;
repository.save(new Statistic( "ressourceIpv4", d));
return "Done";
}

/////////////////////////////Delete////////////////////////////////////

@RequestMapping("/statistics/delete")
public String deleteById(@RequestParam("id") long stat_id) {
repository.delete(stat_id);
return "done";

}

/////////////////////////////Update////////////////////////////////////

@RequestMapping("/statistics/update")
public String update(@RequestParam("id") long stat_id) {
Date d =new Date();	
Statistic stat = repository.findOne(stat_id);
stat.setData("modified");
repository.save(stat);
return "done";
}	

/////////////////////////////Findall////////////////////////////////////

@RequestMapping("/statistics/findall")
public String findAll(){
String result = "";

for(Statistic stat : repository.findAll()){
result += stat.toString() + "<br>";
}	

return result;	
}

/////////////////////////////findbyid////////////////////////////////////

@RequestMapping("/statistics/findbyid")
public String findById(@RequestParam("id") long stat_id){
String result = "";
result = repository.findOne(stat_id).toString();
return result;
}

//-------------------------------------TestChart----------------------------------------//
@RequestMapping(value = "/statistics/chart", method=RequestMethod.GET)
public ModelAndView chart() {
    ModelAndView model = new ModelAndView() ;
    //first, add the regional sales
    Integer northeastSales = 17089;
    Integer westSales = 10603;
    Integer midwestSales = 5223;
    Integer southSales = 10111;
    
    model.addObject("northeastSales", northeastSales);
    model.addObject("southSales", southSales);
    model.addObject("midwestSales", midwestSales);
    model.addObject("westSales", westSales);
    
    //now add sales by lure type
    List<Integer> inshoreSales = Arrays.asList(4074, 3455, 4112);
    List<Integer> nearshoreSales = Arrays.asList(3222, 3011, 3788);
    List<Integer> offshoreSales = Arrays.asList(7811, 7098, 6455);
    
    model.addObject("inshoreSales", inshoreSales);
    model.addObject("nearshoreSales", nearshoreSales);
    model.addObject("offshoreSales", offshoreSales);
    model.setViewName("chart");
	return model;

}



}
