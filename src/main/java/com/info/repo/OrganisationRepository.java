package com.info.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.info.model.Organisation;

public interface OrganisationRepository extends CrudRepository<Organisation, Long>{
	List<Organisation> findByorgname(String org_name);
	Organisation findByOrgname(String org_name);

	
}