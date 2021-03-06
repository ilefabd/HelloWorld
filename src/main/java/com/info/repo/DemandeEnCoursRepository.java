package com.info.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.info.model.DemandeEnCours;
@Repository
public interface DemandeEnCoursRepository extends CrudRepository<DemandeEnCours, Serializable> {
	 List<DemandeEnCours> findByStatus(String status);

}
