package com.info;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.net.whois.WhoisClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.info.ip.Ipv4;
import com.info.model.Response;
import com.info.repo.ResponseRepository;

public class Whois {

	

	public static void main(String[] args) throws UnknownHostException {
		WhoisClient whois;

        whois = new WhoisClient();
       
       
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
		try {
			InetAddress inet = InetAddress.getByName("23.51.243.166");
	        System.out.println(inet.getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 try {
	         InetAddress[] myHost = InetAddress.getAllByName("google.com");
	         for(InetAddress host:myHost){
	            System.out.println(host.getHostAddress());
	         }
	      } catch (UnknownHostException ex) {
	         ex.printStackTrace();
	      }
		 
		 
		 	StringBuilder sb = new StringBuilder("");
		      WhoisClient wic = new WhoisClient();
		      try {
		    	  InetAddress inet = InetAddress.getByName("23.51.243.166");
			        System.out.println(inet.getHostName());
			        String example = inet.getHostName();
			        System.out.println(example.substring(example.lastIndexOf(".")));
		    	    String domain = example.substring(example.lastIndexOf(".")+1);

		         wic.connect(WhoisClient.DEFAULT_HOST);
		         String whoisData1 = wic.query("=" + domain);
		         sb.append(whoisData1);
		         wic.disconnect();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		      System.out.println(sb.toString());
		   }
		 
		 
		 
	   }  

	
    


