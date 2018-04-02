package com.info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="response")
public class Response {


	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	

	@Column(name = "response")
	private String response;
    
	@Column(name="Organisation")
	private String organisation ;

	
	@Column(name="id_demande")
	private Long id_demande ;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		this.response = response;
	}


	@Override
	public String toString() {
		return "Response [id=" + id + ", response=" + response + ", organisation=" + organisation + ", id_demande="
				+ id_demande + "]";
	}


	
	
	


	public Response(String response, String organisation) {
		super();
		this.response = response;
		this.organisation = organisation;
	}


	public Response() {
		super();
		
	}


	public String getOrganisation() {
		return organisation;
	}


	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}


	public Long getId_demande() {
		return id_demande;
	}


	public void setId_demande(Long id_demande) {
		this.id_demande = id_demande;
	}


	
	
}
