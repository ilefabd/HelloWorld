package com.info.repo;

import java.io.Serializable;
import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.info.model.Invoice;
import com.info.model.Response;
import com.info.model.SurveyAnswerStatistics;

public interface ResponseRepository extends CrudRepository<Response, Serializable>  {

	@Query("SELECT " +
		       "    new com.info.model.SurveyAnswerStatistics(v.response,count(v)) " +
		       "FROM " +
		       "    Response v " +
		       "GROUP BY " +
		       "    v.organisation ,v.response")
	public List<SurveyAnswerStatistics> findSurveyCount();	
	
	@Query("SELECT count(response) FROM Response ")	      
	public Integer  findResponseCount();	

	
	@Query("SELECT " +
		       "    new com.info.model.Response(v.response , v.organisation) " +
		       "FROM " +
		       "    Response v " + "WHERE "+ "response= :response ")
    public Response  findbyadress(@Param("response") String response) ;
	@Query("SELECT " +
		       "    new com.info.model.Response(v.response,v.organisation) " +
		       "FROM " +
		       "    Response v " + "WHERE "+ "organisation= :organisation ")
	 public List<Response>  findbyOrganisation(@Param("organisation") String organisation) ;

	
	@Query("SELECT " +
		       "    new com.info.model.Response(v.organisation,sum(size))" +
		       "FROM " +
		       "    Response v " +
		       "GROUP BY  " +
		       " organisation")
	public List<Response> ressourcesParOrganisation();	
	
	
	
}
