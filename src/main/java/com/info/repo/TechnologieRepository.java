package com.info.repo;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

 import com.info.model.Technologie;
@Repository("technologierepository")
public interface TechnologieRepository extends CrudRepository<Technologie, Serializable> {

	
	Technologie findById (Long id);
	
}
