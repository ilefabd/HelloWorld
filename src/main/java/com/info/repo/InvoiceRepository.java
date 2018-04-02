package com.info.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.info.model.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
