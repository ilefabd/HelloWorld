package com.info.repo;

import org.springframework.data.repository.CrudRepository;

import com.info.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
