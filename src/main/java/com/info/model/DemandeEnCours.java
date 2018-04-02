package com.info.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="demandeEnCours")
public class DemandeEnCours {

	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id ;
	
	
	@Column(name = "prefix")
	private String prefix;

	
	@Column(name = "technologie")
	private String technologie;
	
	@Column(name="date")
	private Date date ;

	@Column(name="organisation")
	private String organisation ;

	@Column(name="description")
	private String description ;
 
	@Column(name="status")
	private String status ;
	
	@Column(name="email")
	private String  email ;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prefix_id")
    private Prefix prefi;

	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technologie_id")
	private Technologie tech ;
	
	
	
	
	public Technologie getTech() {
		return tech;
	}

	public void setTech(Technologie tech) {
		this.tech = tech;
	}

	public Prefix getPrefi() {
		return prefi;
	}

	public void setPrefi(Prefix prefi) {
		this.prefi = prefi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTechnologie() {
		return technologie;
	}

	public void setTechnologie(String technologie) {
		this.technologie = technologie;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
  
	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
     
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	
	public DemandeEnCours(String prefix, String technologie, Date date, String organisation, String description,
			String status, String email, Prefix prefi, Technologie tech) {
		super();
		this.prefix = prefix;
		this.technologie = technologie;
		this.date = date;
		this.organisation = organisation;
		this.description = description;
		this.status = status;
		this.email = email;
		this.prefi = prefi;
		this.tech = tech;
	}

	public DemandeEnCours() {
		super();
	}

	@Override
	public String toString() {
		return "DemandeEnCours [id=" + id + ", prefix=" + prefix + ", technologie=" + technologie + ", date=" + date
				+ "]";
	}
	
	
}
