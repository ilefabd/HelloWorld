package com.info.repo;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.info.model.Prefix;
@Repository("prefixrepositoy")
public interface PrefixRepository extends CrudRepository<Prefix, Serializable> {

	Prefix findById (Long id);

}
