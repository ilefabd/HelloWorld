package com.info.repo;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.info.model.Response;

public interface ResponseRepository extends CrudRepository<Response, Serializable> {
 
	
	
}
