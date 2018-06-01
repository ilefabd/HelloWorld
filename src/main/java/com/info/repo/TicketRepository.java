package com.info.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.info.model.Invoice;
import com.info.model.Ticket;
import com.info.model.User;
public interface TicketRepository extends CrudRepository<Ticket, Long> {
	@Query("SELECT " +
		       "    new com.info.model.Ticket(v.Subjet,v.title,v.From,v.Response,v.Opened) " +
		       "FROM " +
		       "    Ticket v " + "WHERE "+ "email= :email ")
public List<Ticket>  findbyEmail(@Param("email") String email) ;
	 
	
//	List<Ticket> findByresponse(String Response);

}
