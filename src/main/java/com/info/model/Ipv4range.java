package com.info.model;

import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.info.ip.Ipv4Range;

@Entity
@Table(name="ipv4range")
public class Ipv4range {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id ;
	
	
	@Column(name = "ip4range")
	private Ipv4Range iprange;
	@Column(name = "range")
	@NotEmpty(message = "*Please add  a new Ip range")
	private String range;

	public Ipv4range(Ipv4Range iprange) {
		super();
		this.iprange = iprange;

	}

	public Ipv4range() {
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Ipv4Range getIprange() {
		return iprange;
	}


	public void setIprange(Ipv4Range ipv4Range) {
		this.iprange = ipv4Range;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	@Override
	public String toString() {
		return "Ipv4range [id=" + id + ", iprange=" + iprange + "]";
	}




	

}
