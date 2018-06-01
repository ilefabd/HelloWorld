package com.info.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="technologie")
public class Technologie {

	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
 
	@Column(name = "technologie")
	private String technologie;

	 @OneToMany(mappedBy = "tech", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
		private Set<DemandeEnCours> demandes;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTechnologie() {
		return technologie;
	}

	public void setTechnologie(String technologie) {
		this.technologie = technologie;
	}

	public Technologie() {
		super();
	}

	public Technologie( String technologie) {
		super();
		this.technologie = technologie;
	}

	@Override
	public String toString() {
		return "Technologie [id=" + id + ", technologie=" + technologie + "]";
	}
	
	
	
	
	
}
