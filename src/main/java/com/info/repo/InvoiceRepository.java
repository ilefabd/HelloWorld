package com.info.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.info.model.Invoice;
import com.info.model.Response;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
	@Query("SELECT " +
		       "    new com.info.model.Invoice(v.invoiceNumber,v.Organisation,v.issue_date,v.status , v.total_amount) " +
		       "FROM " +
		       "    Invoice v " + "WHERE "+ "Organisation= :Organisation ")
 public List<Invoice>  findbyadress(@Param("Organisation") String Organisation) ;
}
