package com.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.info.model.*;
import com.info.repo.PrefixRepository;
@Component
public class StringToPrefix  implements Converter<String, Prefix>{

	
	@Autowired
	private PrefixRepository repository; //Or the class that implments the repository.

	    @Override
	    public Prefix convert(String arg0) {
	        Long id = new Long(arg0);
	        return repository.findOne(id);
	    }
}
