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
@Table(name = "prefix")
public class Prefix {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
 
	@Column(name = "prefix")
	private String prefix;

	public Prefix(String prefix) {
		super();
	
		this.prefix = prefix;
	}

	 @OneToMany(mappedBy = "prefi", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<DemandeEnCours> demandes;
	 

	public Set<DemandeEnCours> getDemandes() {
		return demandes;
	}

	public void setDemandes(Set<DemandeEnCours> demandes) {
		this.demandes = demandes;
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

	
	@Override
	public String toString() {
		return "Prefix [id=" + id + ", prefix=" + prefix + "]";
	}

	public Prefix() {
		super();
	}
 
	
 
	
	
}
