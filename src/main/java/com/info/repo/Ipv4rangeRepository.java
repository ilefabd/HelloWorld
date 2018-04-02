package com.info.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.info.ip.Ipv4Range;
import com.info.model.*;

@Repository("ipv4rangerepository")
public interface Ipv4rangeRepository extends CrudRepository<Ipv4range, Serializable>{

	@Query("SELECT count(iprange) FROM Ipv4range ")	      
	public Integer  findiprangeCount();

	public Ipv4range findByrange(String range);	
}