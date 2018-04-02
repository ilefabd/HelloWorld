package com.info.ip;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
@Converter
public class IprangeConverter implements AttributeConverter<Ipv4Range, String>{

	@Override
	 public String convertToDatabaseColumn(Ipv4Range color) {
	  StringBuilder sb = new StringBuilder();
	  
	  return sb.toString();
	 }

	@Override
	public Ipv4Range convertToEntityAttribute(String ip4range) {
		  
		return null;
	}
}
