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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.info.PdfGenaratorUtil;
import com.info.model.Response;
import com.info.model.Statistic;
import com.info.model.SurveyAnswerStatistics;
import com.info.model.Ticket;
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
	
	
	
	@RequestMapping(value="/statistics", method=RequestMethod.GET)
	
		public ModelAndView RessourceSurvey(){
		ModelAndView model =new ModelAndView() ;
		       List<SurveyAnswerStatistics> result = responserepository.findSurveyCount();
		       Integer ipalloue =responserepository.findResponseCount();
		       Integer ipdisponible =iprangerepository.findiprangeCount();

		       Long y = null;
		       String x = "" ;
		       List<Integer> inshoreSales =  new ArrayList<>() ;
	            List<String> organisation =  new ArrayList<>();

		       for (int i = 0 ; i<result.size();i++)
		       {
		    	   
				     y = result.get(i).getCnt();
			         x = result.get(i).getOrganisation();
			         Integer count = (int) (long) y;
		       
                     inshoreSales.add(count);
                     organisation.add(x);
              
			         


				     
		       }
		     System.out.println(ipalloue);
		     System.out.println(ipdisponible);

		      //pie chart 
		        Integer v = 10 ;
		        model.addObject("IPStatique", v);

		        model.addObject("midwestSales", ipdisponible);
		        model.addObject("westSales", ipalloue);
		       
		      //Column chart
		           model.addObject("x", organisation);
  
		           // System.out.println(inshoreSales.toString());
			        model.addObject("inshoreSales", inshoreSales);
			        model.addObject("organisation", organisation);
		          //  System.out.println(organisation.toString());

		            model.setViewName("admin/home");
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
public String chart(Model model) {
    
    //first, add the regional sales
    Integer northeastSales = 17089;
    Integer westSales = 10603;
    Integer midwestSales = 5223;
    Integer southSales = 10111;
    
    model.addAttribute("northeastSales", northeastSales);
    model.addAttribute("southSales", southSales);
    model.addAttribute("midwestSales", midwestSales);
    model.addAttribute("westSales", westSales);
    
    //now add sales by lure type
    List<Integer> inshoreSales = Arrays.asList(4074, 3455, 4112);
    List<Integer> nearshoreSales = Arrays.asList(3222, 3011, 3788);
    List<Integer> offshoreSales = Arrays.asList(7811, 7098, 6455);
    
    model.addAttribute("inshoreSales", inshoreSales);
    model.addAttribute("nearshoreSales", nearshoreSales);
    model.addAttribute("offshoreSales", offshoreSales);
    
    return "admin/home";
}



}
